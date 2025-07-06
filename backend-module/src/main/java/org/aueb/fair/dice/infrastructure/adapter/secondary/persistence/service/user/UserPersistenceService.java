package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.service.user;

import lombok.RequiredArgsConstructor;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.mapper.user.UserEntityMapper;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.repository.UserRepository;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Persistence adapter implementation of {@link UserPersistencePort}.
 * Responsible for converting between domain models and entities and
 * delegating storage operations to the underlying JPA repository.
 */
@Service
@RequiredArgsConstructor
public class UserPersistenceService implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    /**
     * Saves a domain user by converting it to a persistence entity.
     *
     * @param user the domain user to persist
     */
    @Override
    public void save(User user) {
        userRepository.save(userEntityMapper.mapToEntity(user));
    }

    /**
     * Finds a domain user by their username by querying the repository and mapping the entity to domain.
     *
     * @param username the username to search for
     * @return an Optional containing the domain user if found
     */
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(userEntityMapper::mapFromEntity);
    }

    /**
     * Finds a domain user by their email by querying the repository and mapping the entity to domain.
     *
     * @param email the username to search for
     * @return an Optional containing the domain user if found
     */
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::mapFromEntity);
    }

    /**
     * Fetches a user by id.
     *
     * @param id the user unique identifier.
     * @return an optional of the user.
     */
    @Override
    public Optional<User> findById(final Long id) {
        return this.userRepository.findById(id).map(this.userEntityMapper::mapFromEntity);
    }
}
