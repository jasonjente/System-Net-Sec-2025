package org.aueb.fair.dice.application.service.user;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.domain.user.LoginRequest;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.port.secondary.user.UserPersistencePort;
import org.aueb.fair.dice.security.JwtService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service implementation of {@link UserQueryPort}.
 * Handles read-only access to the User domain by delegating to the persistence port.
 */
@Service
@RequiredArgsConstructor
public class UserQueryService implements UserQueryPort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Retrieves a user by their unique username.
     *
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty if not found
     */
    @Override
    public Optional<User> findByUsername(final String username) {
        return userPersistencePort.findByUsername(username);
    }

    /**
     * Performs the login request for a set of username and password values.
     *
     * @param loginRequest the login request.
     * @return the jwt token created.
     */
    @Override
    public String login(final LoginRequest loginRequest) {
        User user = userPersistencePort.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Invalid credentials");
        }
        return jwtService.generateToken(user);
    }
}
