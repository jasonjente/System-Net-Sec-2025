package org.aueb.fair.dice.application.service.session;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.port.primary.session.SessionCommandPort;
import org.aueb.fair.dice.application.port.secondary.session.SessionPersistencePort;
import org.aueb.fair.dice.domain.session.Session;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class SessionCommandService implements SessionCommandPort {

    private final SessionPersistencePort sessionPersistencePort;

    /**
     * @param session
     * @return
     */
    @Override
    public Long createSession(Session session) {
        // this.sessionValidationService.validate(session);

        return this.sessionPersistencePort.create(session);
    }

    /**
     * @param id
     * @param session
     */
    @Override
    public void updateSession(Long id, Session session) {
        // this.sessionValidationService.validateUpdate(session);

        this.sessionPersistencePort.update(id, session);
    }

    /**
     * @param id
     */
    @Override
    public void deleteSession(Long id) {
        this.sessionPersistencePort.delete(id);
    }
}
