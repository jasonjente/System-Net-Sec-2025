package org.aueb.fair.dice.application.port.secondary.user;

import org.aueb.fair.dice.domain.user.User;

import java.util.Optional;

/**
 * Secondary port for persistence-related operations in the user domain.
 * This interface abstracts access to the underlying data store (e.g., JPA/Hibernate).
 * Implemented by adapter layer and used by command/query services.
 */
public interface UserPersistencePort {

    /**
     * Persists a new or existing user.
     *
     * @param user the domain user to be stored
     */
    void save(User user);

    /**
     * Retrieves a user by their username.
     *
     * @param username the username to search for
     * @return an Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Finds a domain user by their email by querying the repository and mapping the entity to domain.
     *
     * @param email the username to search for
     * @return an Optional containing the domain user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Fetches a user by id.
     *
     * @param id the user unique identifier.
     * @return an optional of the user.
     */
    Optional<User> findById(Long id);
}
