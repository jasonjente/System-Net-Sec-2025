package org.aueb.fair.dice.application.service.game;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.user.UserAlreadyInGameException;
import org.aueb.fair.dice.application.exception.user.UserNotFoundException;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.application.port.secondary.security.HashingPort;
import org.aueb.fair.dice.application.port.secondary.game.GamePersistencePort;
import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.secondary.dice.RandomResourceGeneratorService;
import org.aueb.fair.dice.infrastructure.adapter.secondary.security.SHA256HashingService;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class GameService {

    private final GamePersistencePort gamePersistencePort;
    private final UserQueryPort userQueryPort;

    public String startGame(final String randomClientString, final Long userId) {
        var user = userQueryPort.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User couldn't be retrieved."));
        this.verifyIfUserHasIncompleteGameInProgress(user);

        var gameResult = buildGameResult(randomClientString, user);

        var persistedResult = this.gamePersistencePort.create(gameResult);
        var hashingPort = new SHA256HashingService();
        return hashingPort.hashConcatenation(String.valueOf(persistedResult.getServerGuess()),
                persistedResult.getRandomClientString(), persistedResult.getRandomServerString());
    }

    private static GameResult buildGameResult(String randomClientString, User user) {
        RandomResourceGeneratorService randomNumberGeneratorService = new RandomResourceGeneratorService();
        Integer serverGuess = randomNumberGeneratorService.nextInt(7); // the outer bound is exclusive [0, 6]
        String randomServerString = randomNumberGeneratorService.nextString();

        return GameResult.builder()
                .serverGuess(serverGuess)
                .randomServerString(randomServerString)
                .randomClientString(randomClientString)
                .user(user)
                .build();
    }

    private void verifyIfUserHasIncompleteGameInProgress(final User user) {
        var unfinishedGames = this.gamePersistencePort.getGameResultsByUserId(user.getId());

        if (!unfinishedGames.isEmpty()) {
            throw new UserAlreadyInGameException("User has some unfinished business.. " +
                    "Found " + unfinishedGames.size() + " games that were not finished");
        }

    }

    public GameResult loadClientGuess(final Integer clientGuess, final Long userId) {
        var unfinishedGames = this.gamePersistencePort.getGameResultsByUserId(userId);
        if (unfinishedGames.isEmpty()) {
            throw new UserAlreadyInGameException("No game has been created");
        }
        var gameResult = unfinishedGames.getFirst(); // Business Logic prevents more than one active games at once for a user id.
        var hashingPort = new SHA256HashingService();
        var clientHash = extractHashedGuess(clientGuess, hashingPort, gameResult);
        var serverHash = extractHashedGuess(gameResult.getServerGuess(), hashingPort, gameResult);

        var clientWins = clientHash.equals(serverHash);
        gameResult.setWin(clientWins);
        gameResult.setEnded(true);
        this.gamePersistencePort.update(gameResult);
        return gameResult;
    }

    private String extractHashedGuess(final Integer guessedNumber,
                                           final HashingPort hashingPort,
                                           final GameResult gameResult) {
        return hashingPort.hashConcatenation(String.valueOf(guessedNumber),
                gameResult.getRandomClientString(), gameResult.getRandomServerString());
    }

}
