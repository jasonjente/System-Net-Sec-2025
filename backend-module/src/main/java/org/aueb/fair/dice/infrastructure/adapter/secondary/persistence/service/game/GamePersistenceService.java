package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.service.game;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.EntityNotFoundException;
import org.aueb.fair.dice.application.port.secondary.game.GamePersistencePort;
import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.game.GameResultEntityMapper;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository.GameResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GamePersistenceService implements GamePersistencePort {

    private final GameResultRepository gameResultRepository;
    private final GameResultEntityMapper gameResultEntityMapper;
    /**
     * Persists a new or existing gameResult.
     *
     * @param gameResult the domain gameResult to be stored
     */
    @Override
    public GameResult create(GameResult gameResult) {
        var gameResultEntity = this.gameResultEntityMapper.mapToEntity(gameResult);
        return this.gameResultEntityMapper.mapFromEntity(this.gameResultRepository.save(gameResultEntity));
    }

    /**
     * Updates a gameResult.
     *
     * @param gameResult the game result that will be updated.
     */
    @Override
    public void update(final GameResult gameResult) {
        var id = gameResult.getId();
        var gameResultEntity = this.gameResultRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Game result not found"));

        gameResultEntity.setWin(gameResultEntity.isWin());
        gameResultEntity.setEnded(gameResult.getEnded());
        this.gameResultRepository.save(gameResultEntity);
    }

    /**
     * @param id the identifier of the game result that will be deleted.
     */
    @Override
    public void delete(final Long id) {
        this.gameResultRepository.deleteById(id);
    }

    /**
     * Fetches a game result by the user identifier.
     *
     * @param userId the associated user identifier.
     * @return a game result
     */
    @Override
    public List<GameResult> getGameResultsByUserId(final Long userId) {
        return this.gameResultRepository.findByUser_Id(userId).stream()
                .map(this.gameResultEntityMapper::mapFromEntity)
                .toList();
    }

}
