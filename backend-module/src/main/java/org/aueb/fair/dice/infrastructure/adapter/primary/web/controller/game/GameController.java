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

    @PostMapping("/{clientRandomString}")
    public ResponseEntity<String> startGame(final @PathVariable String clientRandomString,
                                            final @AuthenticationPrincipal User principal) {
        log.info("received start game request, input string: {}, user: {}", clientRandomString, principal.getUsername());
        String hashResult = gamePort.startGame(clientRandomString, extractAuthenticatedUser(principal).getId());
        log.info("Returning hash: {}", hashResult);
        return ResponseEntity.ok(hashResult);
    }

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
