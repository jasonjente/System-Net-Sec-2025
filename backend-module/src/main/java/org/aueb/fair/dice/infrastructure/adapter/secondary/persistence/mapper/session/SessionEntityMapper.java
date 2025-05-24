package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.session;

import org.aueb.fair.dice.domain.session.Session;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.session.SessionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SessionEntityMapper {
    SessionEntity mapToEntity(Session domain);
    Session mapFromEntity(SessionEntity entity);
}
