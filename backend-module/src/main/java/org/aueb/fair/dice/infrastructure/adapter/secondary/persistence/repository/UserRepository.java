package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository;

import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
