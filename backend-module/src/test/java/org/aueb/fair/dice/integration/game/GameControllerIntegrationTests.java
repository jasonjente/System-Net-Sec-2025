package org.aueb.fair.dice.integration.game;

import org.aueb.fair.dice.integration.BaseIntegrationTests;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.JwtResponse;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.LoginRequestDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.UserRegisterRequestDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.game.GameResultDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Sequential integration test for GameController:
 * - Register & login user
 * - Start game
 * - Make client guess
 */
class GameControllerIntegrationTests extends BaseIntegrationTests {

    @Test
    void fullGameFlow_shouldReturnGameHashAndResult() {
        // 1) register
        var register = new UserRegisterRequestDTO(
                "First", "Last", "player1", "P4ssw0rd!", "player1@test.com"
        );
        var regResp = restTemplate.postForEntity(
                getBaseUrl("/api/auth/register"), register, Void.class
        );
        assertThat(regResp.getStatusCode()).isEqualTo(HttpStatus.OK);

        // 2) login
        var loginReq = new LoginRequestDTO("player1", "P4ssw0rd!");
        var loginResp = restTemplate.postForEntity(
                getBaseUrl("/api/auth/login"), loginReq, JwtResponse.class
        );
        assertThat(loginResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String token = loginResp.getBody().token();
        assertThat(token).isNotBlank();

        // prepare headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        // 3) start game
        String clientString = "abc123";
        var startEntity = new HttpEntity<Void>(headers);
        var startResp = restTemplate.exchange(
                getBaseUrl("/api/game/" + clientString),
                HttpMethod.POST, startEntity, String.class
        );
        assertThat(startResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        String hash = startResp.getBody();
        assertThat(hash).isNotBlank();

        // 4) client guess (use 0, unlikely to match)
        var guessEntity = new HttpEntity<Void>(headers);
        var guessResp = restTemplate.exchange(
                getBaseUrl("/api/game/guess/0"),
                HttpMethod.POST, guessEntity, GameResultDTO.class
        );
        assertThat(guessResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        GameResultDTO dto = guessResp.getBody();
        assertThat(dto).isNotNull();
        assertThat(dto.getRandomClientString()).isEqualTo(clientString);
        assertThat(dto.getServerGuess()).isBetween(0, 6);
        assertThat(dto.isWin()).isIn(true, false);
    }
}
