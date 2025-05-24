package org.aueb.fair.dice.application.service.session;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.EntityNotFoundException;
import org.aueb.fair.dice.application.port.primary.session.SessionQueryPort;
import org.aueb.fair.dice.application.port.secondary.session.SessionPersistencePort;
import org.aueb.fair.dice.domain.session.Session;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionQueryService implements SessionQueryPort {

    private final SessionPersistencePort sessionPersistencePort;

    /**
     * @param id
     */
    @Override
    public Session getSessionById(Long id) {
        // TODO
        return this.sessionPersistencePort.getSessionById(id).orElseThrow(() -> new EntityNotFoundException("Session doesn't exist"));
    }
}
