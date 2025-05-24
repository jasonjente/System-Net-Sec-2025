package org.aueb.fair.dice.application.port.secondary.session;

import org.aueb.fair.dice.domain.session.Session;

import java.util.Optional;

/**
 * Secondary port for persistence-related operations in the session domain.
 * This interface abstracts access to the underlying data store (e.g., JPA/Hibernate).
 * Implemented by adapter layer and used by command/query services.
 */
public interface SessionPersistencePort {

    /**
     * Persists a new or existing session.
     *
     * @param session the domain session to be stored
     */
    Long create(Session session);

    /**
     * Updates a session.
     * @param id
     * @param session
     */
    void update(Long id, Session session);

    void delete(Long id);

    Optional<Session> getSessionById(Long id);

}
