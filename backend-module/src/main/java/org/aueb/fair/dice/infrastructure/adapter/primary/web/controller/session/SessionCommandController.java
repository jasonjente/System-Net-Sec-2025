package org.aueb.fair.dice.infrastructure.adapter.primary.web.controller.session;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.session.SessionCommandPort;
import org.aueb.fair.dice.configuration.security.sanitization.InputSanitizer;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.session.SessionDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.session.SessionDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
@Slf4j
public class SessionCommandController {

    private final SessionCommandPort sessionCommandPort;
    private final SessionDTOMapper sessionDTOMapper;

    // TODO HTTP JWT Header -> deutero xrono
    @PostMapping
    public ResponseEntity<Long> create(final @Valid @RequestBody SessionDTO sessionDTO) {
        var sanitizedRequest = InputSanitizer.sanitize(sessionDTO);
        var session = this.sessionDTOMapper.mapFromDTO(sessionDTO);
        var id = sessionCommandPort.createSession(session);
        return ResponseEntity.ok().body(id);
    }

    // TODO HTTP JWT Header
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(final @Valid @RequestBody SessionDTO sessionDTO,
                                       final @PathVariable Long id) {
        var sanitizedRequest = InputSanitizer.sanitize(sessionDTO);
        var session = this.sessionDTOMapper.mapFromDTO(sessionDTO);
        sessionCommandPort.updateSession(id, session);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(final @PathVariable Long id) {
        this.sessionCommandPort.deleteSession(id);
        return ResponseEntity.noContent().build();
    }

}
