package org.aueb.fair.dice.domain.session;

import lombok.*;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {

    //TODO Start adding fields for session, pass them along to entity and DTO use the same names in the variables.

    /**
     * Unique identifier of the user (typically from the JWT 'sub' claim).
     */
    private Long userId;

    /**
     * Notes related to session
     */
    private String notes;

    /**
     * Unique username used for authentication and identification.
     */
    private String username;

    /**
     * Timestamp indicating when the session (or token) was issued.
     */
    private Instant issuedAt;

    /**
     * Timestamp indicating when the session (or token) expires.
     */
    private Instant expiresAt;

    /**
     * Indicates whether the user has been declared the winner in the game.
     */
    private boolean isWinner;
}
