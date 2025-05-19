package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper;

import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity mapToEntity(User domain);
    User mapFromEntity(UserEntity entity);
}
