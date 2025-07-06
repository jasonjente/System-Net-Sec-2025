package org.aueb.fair.dice.secondary.persistence.service;

import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.user.UserEntity;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.user.UserEntityMapper;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository.UserRepository;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.service.user.UserPersistenceService;
import org.aueb.fair.dice.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserPersistenceServiceTest {

    private UserRepository userRepository;
    private UserEntityMapper userEntityMapper;
    private UserPersistenceService service;

    @BeforeEach
    void setup() {
        userRepository = mock(UserRepository.class);
        userEntityMapper = mock(UserEntityMapper.class);
        service = new UserPersistenceService(userRepository, userEntityMapper);
    }

    @Test
    void save_shouldMapDomainAndDelegateToRepository() {
        User user = new User(null, "Bob", "Brown", "bob", "encoded", "test@email.com");
        UserEntity entity = new UserEntity();

        when(userEntityMapper.mapToEntity(user)).thenReturn(entity);

        service.save(user);

        verify(userEntityMapper).mapToEntity(user);
        verify(userRepository).save(entity);
    }

    @Test
    void findByUsername_shouldMapEntityToDomain() {
        UserEntity entity = new UserEntity(1L, "Jane", "Doe", "jane", "pw", "email@email.com");
        User domain = new User(1L, "Jane", "Doe", "jane", "pw", "test@email.com");

        when(userRepository.findByUsername("jane")).thenReturn(Optional.of(entity));
        when(userEntityMapper.mapFromEntity(entity)).thenReturn(domain);

        Optional<User> result = service.findByUsername("jane");

        assertThat(result).isPresent().contains(domain);
    }
}
