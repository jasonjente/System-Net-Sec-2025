package org.aueb.fair.dice.application.service.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.exception.user.UserAlreadyInGameException;
import org.aueb.fair.dice.application.exception.user.UserNotFoundException;
import org.aueb.fair.dice.application.port.primary.game.GamePort;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.application.port.secondary.game.GamePersistencePort;
import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.secondary.dice.RandomResourceGeneratorService;
import org.aueb.fair.dice.infrastructure.adapter.secondary.security.SHA256HashingService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class GameService implements GamePort {

    /**
     * Bean instance of the port responsible for interacting with game data.
     */
    private final GamePersistencePort gamePersistencePort;

    /**
     * Bean instance of the port responsible for interacting with user data.
     */
    private final UserQueryPort userQueryPort;

    /**
     * Initializes a game by retrieving a random client string that can will be used for the hash verification.
     *
     * @param randomClientString the random client string
     * @param userId             the user id extracted from the jwt token
     * @return the concatenated string of: (server_guess|random_client_string|random_server_string).
     */
    @Override
    public String startGame(final String randomClientString, final Long userId) {
        log.info("Retrieved a request to start a game.");
        var user = userQueryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be retrieved."));
        var unfinishedGames = this.gamePersistencePort.getGameResultsByUserId(user.getId());

        if (!unfinishedGames.isEmpty()) {
            log.info("User has unfinished games, deleting all but the last game.");
            sortByDateDescAndKeepTheLatest(unfinishedGames, randomClientString, user);
        }

        var gameResult =
                unfinishedGames.isEmpty() ? buildGameResult(randomClientString, user) : unfinishedGames.getFirst();
        log.info("Saving new game result.");
        var persistedResult = this.gamePersistencePort.create(gameResult);
        var hashingPort = new SHA256HashingService();
        var hash = hashingPort.hashConcatenation(String.valueOf(persistedResult.getServerGuess()),
                persistedResult.getRandomClientString(), persistedResult.getRandomServerString());

        log.info("Returning new hash to the user.");
        return hash;
    }

    /**
     * Retrieves the client guess and validates it against the value in the database. It will then delete the game
     * result from the database.
     *
     * @param clientGuess the client guess of the dice result.
     * @param userId      the user id extracted from the jwt token
     * @return the game result containing the server guess and the server random string.
     */
    @Override
    public GameResult loadClientGuess(final Integer clientGuess, final Long userId) {
        log.info("Loading client guess.");
        var unfinishedGames = this.gamePersistencePort.getGameResultsByUserId(userId);
        if (unfinishedGames.isEmpty()) {
            log.warn("Client tried to guess without starting a game.");
            throw new UserAlreadyInGameException("No game has been created.");
        } else if (unfinishedGames.size() > 1) {
            sortByDateDescAndKeepTheLatest(unfinishedGames);
        }
        var gameResult = unfinishedGames.getFirst(); // Business Logic prevents more than one active games at once for a user id.
        var clientHash = extractHashedGuess(clientGuess, gameResult);
        var serverHash = extractHashedGuess(gameResult.getServerGuess(), gameResult);

        var clientWins = clientHash.equals(serverHash);
        log.info("Setting game to ended state.");
        gameResult.setWin(clientWins);
        gameResult.setEnded(true);
        this.gamePersistencePort.delete(gameResult.getId());
        return gameResult;
    }

    private void sortByDateDescAndKeepTheLatest(final List<GameResult> unfinishedGames,
                                                final String randomClientString, final User user) {
        if (unfinishedGames.size() > 1) {
            unfinishedGames.sort(Comparator.comparing(GameResult::getTimestamp).reversed());
            unfinishedGames.forEach(gameResult -> this.gamePersistencePort.delete(gameResult.getId()));
        }

        var latest = unfinishedGames.getFirst();
        var newGameResult = buildGameResult(randomClientString, user);
        latest.setRandomClientString(randomClientString);
        latest.setRandomServerString(newGameResult.getRandomServerString());
        latest.setServerGuess(newGameResult.getServerGuess());
        latest.setTimestamp(newGameResult.getTimestamp());
    }

    private void sortByDateDescAndKeepTheLatest(final List<GameResult> unfinishedGames) {
        unfinishedGames.sort(Comparator.comparing(GameResult::getTimestamp).reversed());
        unfinishedGames.stream()
                .skip(1)
                // Delete the rest of the games and keep only the latest.
                .forEach(gameResult -> this.gamePersistencePort.delete(gameResult.getId()));
    }

    private GameResult buildGameResult(final String randomClientString, final User user) {
        RandomResourceGeneratorService randomNumberGeneratorService = new RandomResourceGeneratorService();
        Integer serverGuess = randomNumberGeneratorService.nextInt(7); // the outer bound is exclusive [0, 6)
        String randomServerString = randomNumberGeneratorService.nextString();

        return GameResult.builder()
                .serverGuess(serverGuess)
                .randomServerString(randomServerString)
                .randomClientString(randomClientString)
                .user(user)
                .timestamp(Instant.now().toEpochMilli())
                .build();
    }

    private String extractHashedGuess(final Integer guessedNumber,
                                      final GameResult gameResult) {
        var hashingPort = new SHA256HashingService();
        return hashingPort.hashConcatenation(String.valueOf(guessedNumber),
                gameResult.getRandomClientString(), gameResult.getRandomServerString());
    }

}
