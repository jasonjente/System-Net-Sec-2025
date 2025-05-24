package org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.session;

import org.aueb.fair.dice.domain.session.Session;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.session.SessionDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionDTOMapper {

    SessionDTO mapToDTO(Session session);

    Session mapFromDTO(SessionDTO sessionDTO);
}
