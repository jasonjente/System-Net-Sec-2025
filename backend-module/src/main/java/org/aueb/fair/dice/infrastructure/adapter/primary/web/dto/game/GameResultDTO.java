package org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.game;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GameResultDTO implements Serializable {

    private Long id;

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
     * Indicates whether the user has been declared the winner in the game.
     */
    private boolean win;
}
