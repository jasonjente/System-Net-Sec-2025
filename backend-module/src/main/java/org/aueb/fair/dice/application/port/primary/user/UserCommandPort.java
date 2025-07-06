package org.aueb.fair.dice.application.port.primary.user;

import org.aueb.fair.dice.domain.user.User;

/**
 * Primary application port for handling user-related commands.
 * This interface defines mutation operations related to the user domain,
 * such as registration or password changes. Implemented by the application layer.
 */
public interface UserCommandPort {

    /**
     * Registers a new user in the system.
     * 
     * @param user the user to be registered, must contain valid credentials
     */
    void register(User user);
}
