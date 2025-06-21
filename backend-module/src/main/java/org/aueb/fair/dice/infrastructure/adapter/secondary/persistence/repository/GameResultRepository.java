package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository;

import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.game.GameResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameResultRepository extends JpaRepository<GameResultEntity, Long> {

    /**
     * Fetch all non-winning game results for a given user ID.
     *
     * @param userId the ID of the user
     * @return list of game results for the given user.
     */
    List<GameResultEntity> findByUser_Id(Long userId);
}