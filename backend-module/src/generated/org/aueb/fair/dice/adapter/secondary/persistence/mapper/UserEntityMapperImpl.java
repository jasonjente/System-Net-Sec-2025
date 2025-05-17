package org.aueb.fair.dice.adapter.secondary.persistence.mapper;

import javax.annotation.processing.Generated;
import org.aueb.fair.dice.adapter.secondary.persistence.entity.UserEntity;
import org.aueb.fair.dice.domain.user.User;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-17T10:49:23+0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Oracle Corporation)"
)
@Component
public class UserEntityMapperImpl implements UserEntityMapper {

    @Override
    public UserEntity mapToEntity(User domain) {
        if ( domain == null ) {
            return null;
        }

        UserEntity userEntity = new UserEntity();

        userEntity.setId( domain.getId() );
        userEntity.setFirstName( domain.getFirstName() );
        userEntity.setLastName( domain.getLastName() );
        userEntity.setUsername( domain.getUsername() );
        userEntity.setPassword( domain.getPassword() );

        return userEntity;
    }

    @Override
    public User mapFromEntity(UserEntity entity) {
        if ( entity == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( entity.getId() );
        user.firstName( entity.getFirstName() );
        user.lastName( entity.getLastName() );
        user.username( entity.getUsername() );
        user.password( entity.getPassword() );

        return user.build();
    }
}
