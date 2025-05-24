package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.service.session;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.application.exception.EntityNotFoundException;
import org.aueb.fair.dice.application.port.secondary.session.SessionPersistencePort;
import org.aueb.fair.dice.domain.session.Session;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.session.SessionEntityMapper;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository.SessionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SessionPersistenceService implements SessionPersistencePort {

    private final SessionRepository sessionRepository;
    private final SessionEntityMapper sessionEntityMapper;
    /**
     * Persists a new or existing session.
     *
     * @param session the domain session to be stored
     */
    @Override
    public Long create(Session session) {
        var sessionEntity = this.sessionEntityMapper.mapToEntity(session);
        return this.sessionRepository.save(sessionEntity).getId();
    }

    /**
     * Updates a session.
     *
     * @param id
     * @param session
     */
    @Override
    public void update(Long id, Session session) {
        var sessionEntity = this.sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Session not found"));

        // todo copy stuff from session object to session entity

        this.sessionRepository.save(sessionEntity);
    }

    /**
     * @param id
     */
    @Override
    public void delete(Long id) {
        this.sessionRepository.deleteById(id);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public Optional<Session> getSessionById(Long id) {
        return this.sessionRepository.findById(id).map(this.sessionEntityMapper::mapFromEntity);
    }
}
