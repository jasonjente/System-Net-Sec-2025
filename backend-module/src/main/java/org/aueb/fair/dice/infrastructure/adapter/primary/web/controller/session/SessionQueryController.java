package org.aueb.fair.dice.infrastructure.adapter.primary.web.controller.session;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.session.SessionQueryPort;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.session.SessionDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.session.SessionDTOMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/session")
@RequiredArgsConstructor
@Slf4j
public class SessionQueryController {


    private final SessionQueryPort sessionQueryPort;
    private final SessionDTOMapper sessionDTOMapper;

    @GetMapping("/{id}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable final Long id) {
        var session = this.sessionDTOMapper.mapToDTO(sessionQueryPort.getSessionById(id));
        return ResponseEntity.ok(session);
    }
}
