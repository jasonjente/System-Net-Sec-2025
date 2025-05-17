package org.aueb.fair.dice.web.mapper;

import javax.annotation.processing.Generated;
import org.aueb.fair.dice.domain.user.LoginRequest;
import org.aueb.fair.dice.web.dto.LoginRequestDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-17T10:49:23+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class LoginRequestDTOMapperImpl implements LoginRequestDTOMapper {

    @Override
    public LoginRequest mapFromDTO(LoginRequestDTO loginRequestDTO) {
        if ( loginRequestDTO == null ) {
            return null;
        }

        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername( loginRequestDTO.getUsername() );
        loginRequest.setPassword( loginRequestDTO.getPassword() );

        return loginRequest;
    }

    @Override
    public LoginRequestDTO mapToDTO(LoginRequest loginRequest) {
        if ( loginRequest == null ) {
            return null;
        }

        LoginRequestDTO loginRequestDTO = new LoginRequestDTO();

        loginRequestDTO.setUsername( loginRequest.getUsername() );
        loginRequestDTO.setPassword( loginRequest.getPassword() );

        return loginRequestDTO;
    }
}
