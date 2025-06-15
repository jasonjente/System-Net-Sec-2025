package org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.game;

import org.aueb.fair.dice.domain.game.GameResult;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.game.GameResultDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GameResultDTOMapper {

    GameResultDTO mapToDTO(GameResult gameResult);

    GameResult mapFromDTO(GameResultDTO gameResultDTO);
}
