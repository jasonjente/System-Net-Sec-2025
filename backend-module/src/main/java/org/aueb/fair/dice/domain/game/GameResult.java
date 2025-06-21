package org.aueb.fair.dice.domain.game;

import lombok.*;
import org.aueb.fair.dice.domain.user.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameResult {

    private Long id;
    /**
     * Unique identifier of the user (typically from the JWT 'sub' claim).
     */
    private User user;

    /**
     * The result that the server has guessed.
     */
    private Integer serverGuess;

    /**
     * The client's random signing string.
     */
    private String randomClientString;

    /**
     * The server's random signing string.
     */
    private String randomServerString;

    /**
     * Timestamp of the game creation datetime.
     */
    private Long timestamp;

    /**
     * Indicates whether the user has been declared the winner in the game.
     */
    private boolean win;

    /**
     * Flag indicating whether the game has ended or not.
     */
    @Builder.Default
    private Boolean ended = Boolean.FALSE;
}
