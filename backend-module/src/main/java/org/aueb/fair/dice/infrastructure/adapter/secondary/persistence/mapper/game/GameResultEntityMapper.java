package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.game;

import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.game.GameResultEntity;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.user.UserEntityMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserEntityMapper.class})
public interface GameResultEntityMapper {
    GameResultEntity mapToEntity(GameResult domain);
    GameResult mapFromEntity(GameResultEntity entity);
}
