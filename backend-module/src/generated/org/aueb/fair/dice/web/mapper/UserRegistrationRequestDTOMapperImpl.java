package org.aueb.fair.dice.web.mapper;

import javax.annotation.processing.Generated;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.web.dto.UserRegisterRequestDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-17T10:49:23+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserRegistrationRequestDTOMapperImpl implements UserRegistrationRequestDTOMapper {

    @Override
    public User mapFromDTO(UserRegisterRequestDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.firstName( dto.getFirstName() );
        user.lastName( dto.getLastName() );
        user.username( dto.getUsername() );
        user.password( dto.getPassword() );

        return user.build();
    }

    @Override
    public UserRegisterRequestDTO mapToDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserRegisterRequestDTO userRegisterRequestDTO = new UserRegisterRequestDTO();

        userRegisterRequestDTO.setFirstName( user.getFirstName() );
        userRegisterRequestDTO.setLastName( user.getLastName() );
        userRegisterRequestDTO.setUsername( user.getUsername() );
        userRegisterRequestDTO.setPassword( user.getPassword() );

        return userRegisterRequestDTO;
    }
}
