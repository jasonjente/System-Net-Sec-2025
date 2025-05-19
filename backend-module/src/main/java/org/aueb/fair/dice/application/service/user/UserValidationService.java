package org.aueb.fair.dice.application.service.user;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.user.UserCreationException;
import org.aueb.fair.dice.application.exception.user.UserUpdateException;
import org.aueb.fair.dice.application.port.primary.user.UserValidationPort;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.domain.validation.ValidationErrorCollector;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserValidationService implements UserValidationPort {

    private final UserPersistencePort userPersistencePort;

    /**
     * Performs a validation on a user object that will be created.
     *
     * @param user the user that will be created.
     */
    @Override
    public void validateUserCreation(final User user) {
        var existingUserWithUsername = userPersistencePort.findByUsername(user.getUsername());
        var validationErrorCollector = new ValidationErrorCollector();

        if (existingUserWithUsername.isPresent()) {
            validationErrorCollector.addMessage("The username already exists");
        }

        // if the user provides an id for the creation should either be banned via his IP or
        // if we are on a good day we should just skip the value he is trying to assign himself.
        if (user.getId() != null) {
            user.setId(null);
        }

        if (validationErrorCollector.hasErrors()) {
            throw new UserCreationException(validationErrorCollector.printMessages());
        }
    }

    /**
     * Performs a validation on an existing user object that will be updated.
     *
     * @param user the user that will be created.
     */
    @Override
    public void validateUserUpdate(final User user) {
        var existingUserWithUsername = userPersistencePort.findByUsername(user.getUsername());
        var validationErrorCollector = new ValidationErrorCollector();

        if (existingUserWithUsername.isEmpty()) {
            validationErrorCollector.addMessage("Username doesn't exist.");
        }

        if (user.getId() != null) {
            validationErrorCollector.addMessage("User id cannot be empty.");
        }

        if (validationErrorCollector.hasErrors()) {
            throw new UserUpdateException(validationErrorCollector.printMessages());
        }
    }
}
