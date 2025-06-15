package org.aueb.fair.dice.application.port.secondary.game;

import org.aueb.fair.dice.domain.game.GameResult;

import java.util.List;

public interface GamePersistencePort {

    /**
     * Persists a new or existing game result.
     *
     * @param gameResult the domain game result to be stored
     */
    GameResult create(GameResult gameResult);

    /**
     * Updates a game result.
     * @param gameResult the updated game result
     */
    void update(GameResult gameResult);

    /**
     * Deletes a game result after the completion of the game.
     *
     * @param id the identifier of the game result.
     */
    void delete(Long id);

    /**
     * Fetches a game result by the user identifier.
     *
     * @param userId the associated user identifier.
     * @return a game result
     */
    List<GameResult> getGameResultsByUserId(Long userId);

}
