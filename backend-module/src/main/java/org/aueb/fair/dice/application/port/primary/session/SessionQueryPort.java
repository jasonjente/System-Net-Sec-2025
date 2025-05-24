package org.aueb.fair.dice.application.port.primary.session;

import org.aueb.fair.dice.domain.session.Session;

public interface SessionQueryPort {

    Session getSessionById(Long id);

}
