package org.aueb.fair.dice.infrastructure.adapter.primary.web.controller.session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.application.service.game.GameService;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.game.GameResultDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.game.GameResultDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/game")
@RequiredArgsConstructor
@Slf4j
public class GameController {

    private final GameResultDTOMapper gameResultDTOMapper;
    private final GameService gameService;
    private final UserQueryPort userQueryPort;

    @PostMapping("/{clientRandomString}")
    public ResponseEntity<String> startGame(final @PathVariable String clientRandomString,
                                            final @AuthenticationPrincipal User principal) {
        String hashResult = gameService.startGame(clientRandomString, extractAuthenticatedUser(principal).getId());
        return ResponseEntity.ok(hashResult);
    }

    @PostMapping("/guess/{guess}")
    public ResponseEntity<GameResultDTO> clientGuess(final @PathVariable Integer guess,
                                                     final @AuthenticationPrincipal User principal) {
        var result = gameService.loadClientGuess(guess, extractAuthenticatedUser(principal).getId());
        return ResponseEntity.ok(gameResultDTOMapper.mapToDTO(result));
    }

    private User extractAuthenticatedUser(User principal) {
        return userQueryPort.findByUsername(principal.getUsername())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
    }
}
