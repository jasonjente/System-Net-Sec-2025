package org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper;

import org.aueb.fair.dice.domain.user.LoginRequest;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.LoginRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * MapStruct mapper responsible for converting between {@link LoginRequestDTO}
 * and the domain-layer {@link LoginRequest}.
 *
 * <p>This mapper abstracts the translation of user login data from the web layer
 * to the domain layer and vice versa. It helps enforce a clean separation between
 * incoming request data and the internal domain model.</p>
 *
 * <p>Note: This interface is automatically implemented by MapStruct at build time
 * and registered as a Spring bean due to {@code componentModel = "spring"}.</p>
 */
@Mapper(componentModel = "spring")
public interface LoginRequestDTOMapper {

    LoginRequestDTOMapper INSTANCE = Mappers.getMapper(LoginRequestDTOMapper.class);

    /**
     * Maps a login request DTO from the web layer to a domain-layer login request.
     *
     * @param loginRequestDTO the incoming DTO containing username and password
     * @return the domain object representing the login request
     */
    LoginRequest mapFromDTO(LoginRequestDTO loginRequestDTO);

    /**
     * Maps a domain-layer login request to its web DTO representation.
     *
     * @param loginRequest the internal login request model
     * @return a DTO suitable for transfer or client response
     */
    LoginRequestDTO mapToDTO(LoginRequest loginRequest);
}
