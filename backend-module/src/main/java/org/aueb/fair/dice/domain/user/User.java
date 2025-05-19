package org.aueb.fair.dice.domain.user;

import lombok.*;

/**
 * Domain model representing an application user.
 * Encapsulates user identity and credentials used in business logic.
 * This class is independent of persistence and DTO concerns.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * Unique identifier for the user.
     * May be null for newly created instances before persistence.
     */
    private Long id;

    /**
     * User's first name.
     */
    private String firstName;

    /**
     * User's last name.
     */
    private String lastName;

    /**
     * Unique username used for authentication and identification.
     */
    private String username;

    /**
     * Encrypted user password.
     * Handled via password encoding logic in the application layer.
     */
    private String password;

    /**
     * The user email address.
     */
    private String email;
}
