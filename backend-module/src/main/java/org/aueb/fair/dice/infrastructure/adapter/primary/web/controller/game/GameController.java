package org.aueb.fair.dice.infrastructure.adapter.primary.web.controller.game;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.game.GamePort;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.game.GameResultDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.game.GameResultDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {
    /// Beans
    private final GameResultDTOMapper gameResultDTOMapper;
    private final GamePort gamePort;
    private final UserQueryPort userQueryPort;

    /**
     * Exposes an HTTP POST Endpoint expecting a random client string that can be used
     * for the verification of the validity of the game results.
     *
     * @param clientRandomString the client random string used for validity.
     * @param principal          spring will automatically inject this after the request has passed the spring security
     *                           filters, and it will contain the full User object.
     * @return                   A response entity with 200-OK and the hash result of the server guess.
     */
    @PostMapping("/{clientRandomString}")
    public ResponseEntity<String> startGame(final @PathVariable String clientRandomString,
                                            final @AuthenticationPrincipal User principal) {
        log.info("received start game request, input string: {}, user: {}", clientRandomString, principal.getUsername());
        String hashResult = gamePort.startGame(clientRandomString, extractAuthenticatedUser(principal).getId());
        log.info("Returning hash: {}", hashResult);
        return ResponseEntity.ok(hashResult);
    }

    /**
     * Exposes an HTTP POST Endpoint expecting an integer bound between 1 and 6, which will be the client guess.
     *
     * @param guess     the client guess for the expected outcome of the dice throw.
     * @param principal spring will automatically inject this after the request has passed the spring security
     *                           filters, and it will contain the full User object.
     * @return          returns a DTO containing the game results.
     */
    @PostMapping("/guess/{guess}")
    public ResponseEntity<GameResultDTO> clientGuess(final @PathVariable Integer guess,
                                                     final @AuthenticationPrincipal User principal) {
        log.info("Received guess: {} for user: {}", guess, principal.getUsername());
        var result = gamePort.loadClientGuess(guess, extractAuthenticatedUser(principal).getId());
        log.info("Result - client wins: {}", result.isWin());
        return ResponseEntity.ok(gameResultDTOMapper.mapToDTO(result));
    }

    private User extractAuthenticatedUser(User principal) {
        return userQueryPort.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
}
