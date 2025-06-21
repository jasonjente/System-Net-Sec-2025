package org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.game;

import jakarta.persistence.*;
import lombok.*;
import org.aueb.fair.dice.infrastructure.adapter.secondary.persistence.entity.user.UserEntity;

@Entity
@Builder
@Table(name = "GAME_RESULTS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GameResultEntity {

    /**
     * UUID for the game result.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    /**
     * The user who played the game.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "USER_ID", referencedColumnName = "ID")
    private UserEntity user;

    /**
     * The result that the server has guessed.
     */
    @Column(name = "SERVER_GUESS")
    private Integer serverGuess;

    /**
     * The client's random signing string.
     */
    @Column(name = "RANDOM_CLIENT_STRING")
    private String randomClientString;

    /**
     * The server's random signing string.
     */
    @Column(name = "RANDOM_SERVER_STRING")
    private String randomServerString;

    /**
     * Timestamp of the game creation datetime.
     */
    @Column(name = "GAME_TIME_START")
    private Long timestamp;

    /**
     * Indicates whether the user has been declared the winner in the game.
     */
    @Column(name = "WIN")
    private boolean win;

    /**
     * Flag indicating whether the game has ended or not.
     */
    @Column
    @Builder.Default
    private Boolean ended = Boolean.FALSE;
}
