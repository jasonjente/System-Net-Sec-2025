package org.aueb.fair.dice.application.port.primary.game;

import org.aueb.fair.dice.domain.game.GameResult;

public interface GamePort {

    /**
     * Initializes a game by retrieving a random client string that can will be used for the hash verification.
     *
     * @param randomClientString the random client string
     * @param userId             the user id extracted from the jwt token
     * @return the concatenated string of: (server_guess|random_client_string|random_server_string).
     */
    String startGame(String randomClientString, Long userId);

    /**
     * Finishes a game based on the client guess that is provided. If no game is associated with the client
     * an exception will be thrown returning the appropriate HTTP Code and error message.
     * @param clientGuess the client dice guess.
     * @param userId      the user id that associated with this game.
     * @return            the game result by comparing the stored result and the client guess.
     */
    GameResult loadClientGuess(Integer clientGuess, Long userId);
}
