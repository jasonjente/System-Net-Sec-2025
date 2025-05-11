package org.aueb.fair.dice.port.primary.user;

import org.aueb.fair.dice.domain.user.LoginRequest;
import org.aueb.fair.dice.domain.user.User;

import java.util.Optional;

/**
 * Primary application port for handling user-related queries.
 * This interface defines read-only operations related to the user domain,
 * such as fetching users by identifier or username. Used for queries and authentication.
 */
public interface UserQueryPort {

    /**
     * Finds a user by their unique username.
     *
     * @param username the username to search for
     * @return an Optional containing the User if found, or empty otherwise
     */
    Optional<User> findByUsername(String username);

    /**
     * Performs the login request for a set of username and password values.
     *
     * @param loginRequest the login request.
     * @return the jwt token created.
     */

    String login(LoginRequest loginRequest);
}
