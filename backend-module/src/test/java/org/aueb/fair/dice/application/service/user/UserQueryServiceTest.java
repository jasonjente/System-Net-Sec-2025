package org.aueb.fair.dice.application.service.user;

import org.aueb.fair.dice.domain.user.LoginRequest;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.aueb.fair.dice.configuration.security.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Unit test suite for {@link UserQueryService}.
 *
 * <p>
 * This class validates the behavior of {@link UserQueryService}, specifically:
 * <ul>
 *     <li>Retrieving users by username via {@link UserPersistencePort}</li>
 *     <li>Performing login validation using {@link PasswordEncoder}</li>
 *     <li>Issuing JWT tokens using {@link JwtService} upon successful authentication</li>
 * </ul>
 * </p>
 *
 * <p>
 * The tests use Mockito to mock external dependencies and AssertJ for assertions.
 * </p>
 */
class UserQueryServiceTest {

    private UserPersistencePort userPersistencePort;
    private PasswordEncoder passwordEncoder;
    private JwtService jwtService;
    private UserQueryService service;

    /**
     * Initializes mocks and the service under test before each test method.
     */
    @BeforeEach
    void setup() {
        userPersistencePort = mock(UserPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        jwtService = mock(JwtService.class);
        service = new UserQueryService(userPersistencePort, passwordEncoder, jwtService);
    }

    /**
     * Verifies that {@code findByUsername} returns the expected user
     * from the persistence port when present.
     */
    @Test
    void findByUsername_shouldReturnUserFromPort() {
        User user = new User(1L, "Alice", "Smith", "alice", "encoded", "alice@AliceSmith.com");
        when(userPersistencePort.findByUsername("alice")).thenReturn(Optional.of(user));

        Optional<User> result = service.findByUsername("alice");

        assertThat(result).isPresent().contains(user);
        verify(userPersistencePort).findByUsername("alice");
    }

    /**
     * Ensures that {@code login} returns a JWT token when the username is found
     * and the password matches.
     */
    @Test
    void login_shouldReturnToken_whenCredentialsAreValid() {
        User user = new User(1L, "Alice", "Smith", "alice", "encoded", "alice@AliceSmith.com");
        LoginRequest request = new LoginRequest("alice", "rawpass");

        when(userPersistencePort.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("rawpass", "encoded")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("jwt-token");

        String token = service.login(request);

        assertThat(token).isEqualTo("jwt-token");
        verify(jwtService).generateToken(user);
    }

    /**
     * Verifies that {@code login} throws a {@link BadCredentialsException}
     * if the username is not found.
     */
    @Test
    void login_shouldThrowException_whenUserNotFound() {
        LoginRequest request = new LoginRequest("unknown", "any");

        when(userPersistencePort.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }

    /**
     * Verifies that {@code login} throws a {@link BadCredentialsException}
     * if the password does not match.
     */
    @Test
    void login_shouldThrowException_whenPasswordDoesNotMatch() {
        User user = new User(1L, "Alice", "Smith", "alice", "encoded", "alice@AliceSmith.com");
        LoginRequest request = new LoginRequest("alice", "wrong");

        when(userPersistencePort.findByUsername("alice")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded")).thenReturn(false);

        assertThatThrownBy(() -> service.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessage("Invalid credentials");
    }
}
