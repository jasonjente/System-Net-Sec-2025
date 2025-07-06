package org.aueb.fair.dice.application.service.user;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.user.UserCreationException;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.application.port.primary.user.UserValidationPort;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.domain.validation.ValidationErrorCollector;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserValidationService implements UserValidationPort {

    private final UserQueryPort userQueryPort;

    /**
     * Performs a validation on a user object that will be created.
     *
     * @param user the user that will be created.
     */
    @Override
    public void validateUserCreation(final User user) {
        var existingUserWithUsername = userQueryPort.findByUsername(user.getUsername());
        var validationErrorCollector = new ValidationErrorCollector();

        if (existingUserWithUsername.isPresent()) {
            validationErrorCollector.addMessage("The username already exists");
        }

        var existingUserWithEmail = userQueryPort.findByEmail(user.getEmail());

        if (existingUserWithEmail.isPresent()) {
            validationErrorCollector.addMessage("The email already exists");
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

}
