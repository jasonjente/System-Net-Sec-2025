package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository;

import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.session.SessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<SessionEntity, Long> {

}
