package org.aueb.fair.dice.application.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.user.UserCommandPort;
import org.aueb.fair.dice.application.port.primary.user.UserValidationPort;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.aueb.fair.dice.domain.user.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service implementation of {@link UserCommandPort}.
 *
 * Handles command-side business logic for the User domain.
 * Encodes sensitive data (e.g., passwords) before delegating persistence.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserCommandService implements UserCommandPort {
    private final UserValidationPort userValidationPort;
    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user by encoding the password and storing the user through the persistence port.
     *
     * @param user the user to register
     */
    @Override
    public void register(final User user) {
        log.info("Received a request to register a new user.");
        userValidationPort.validateUserCreation(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userPersistencePort.save(user);
        log.info("User registered successfully");
    }

}
