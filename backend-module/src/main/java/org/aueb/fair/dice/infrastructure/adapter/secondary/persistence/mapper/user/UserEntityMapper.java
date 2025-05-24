package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.user;

import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
    UserEntity mapToEntity(User domain);
    User mapFromEntity(UserEntity entity);
}
