package org.aueb.fair.dice.application.port.primary.user;

import org.aueb.fair.dice.domain.user.User;

public interface UserValidationPort {

    /**
     * Performs a validation on a user object that will be created.
     *
     * @param user the user that will be created.
     */
    void validateUserCreation(User user);

}
