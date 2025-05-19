package org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper;

import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.UserRegisterRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper responsible for converting between {@link UserRegisterRequestDTO}
 * and the domain-level {@link User} object.
 *
 * <p>
 * This mapping abstracts the creation of user domain objects from registration input
 * and allows seamless mapping back to DTOs for response or administrative use.
 * </p>
 *
 * <p>
 * MapStruct will generate the implementation during compilation with Spring component model support.
 * </p>
 */
@Mapper(componentModel = "spring")
public interface UserRegistrationRequestDTOMapper {

    UserRegistrationRequestDTOMapper INSTANCE = Mappers.getMapper(UserRegistrationRequestDTOMapper.class);

    /**
     * Maps a registration request DTO to a domain-level User.
     *
     * @param dto the client-provided registration data
     * @return a {@link User} instance suitable for business logic and persistence
     */
    User mapFromDTO(UserRegisterRequestDTO dto);

    /**
     * Maps a domain-level User to a registration DTO.
     *
     * @param user the domain model
     * @return a {@link UserRegisterRequestDTO} representation, for response or reuse
     */
    UserRegisterRequestDTO mapToDTO(User user);
}
