package org.aueb.fair.dice.application.port.primary.session;

import org.aueb.fair.dice.domain.session.Session;

public interface SessionCommandPort {

    Long createSession(Session session);
    void updateSession(Long id, Session session);
    void deleteSession(Long id);

}
