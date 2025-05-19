package org.aueb.fair.dice.application.service.user;

import org.aueb.fair.dice.application.port.primary.user.UserValidationPort;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class UserCommandServiceTest {
    private UserValidationPort userValidationPort;
    private UserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;
    private UserCommandService service;

    @BeforeEach
    void setup() {
        userValidationPort = mock(UserValidationPort.class);
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoder = new BCryptPasswordEncoder(10);
        service = new UserCommandService(userValidationPort, userPersistencePort, passwordEncoder);
    }

    @Test
    void register_shouldEncodePasswordAndSaveUser() {
        User user = new User(null, "John", "Doe", "jdoe", "rawpass", "jon-doe@joe-doe.com");
        doNothing().when(userValidationPort).validateUserCreation(user);
        service.register(user);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userPersistencePort, times(1)).save(captor.capture());

        User saved = captor.getValue();
        assertThat(saved.getPassword()).isNotEqualTo("rawpass");
        assertThat(passwordEncoder.matches("rawpass", saved.getPassword())).isTrue();
    }
}
