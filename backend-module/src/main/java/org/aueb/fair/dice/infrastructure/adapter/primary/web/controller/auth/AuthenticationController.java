package org.aueb.fair.dice.infrastructure.adapter.primary.web.controller.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aueb.fair.dice.application.port.primary.user.UserCommandPort;
import org.aueb.fair.dice.application.port.primary.user.UserQueryPort;
import org.aueb.fair.dice.configuration.security.sanitization.InputSanitizer;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.JwtResponse;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.LoginRequestDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.UserRegisterRequestDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.LoginRequestDTOMapper;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.mapper.UserRegistrationRequestDTOMapper;
import org.aueb.fair.dice.application.service.user.UserCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller responsible for handling authentication-related operations
 * such as user registration and login.
 *
 * <p>
 * Follows a strict hexagonal architecture design. It uses DTO mappers to
 * convert between web layer DTOs and domain-level objects and delegates
 * core logic to application services.
 * </p>
 *
 * <p>
 * Exposes two main endpoints:
 * <ul>
 *     <li><code>POST /api/auth/register</code> – for registering a new user</li>
 *     <li><code>POST /api/auth/login</code> – for logging in and receiving a JWT</li>
 * </ul>
 * </p>
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final UserCommandPort userCommandPort;
    private final UserQueryPort userQueryPort;
    private final UserRegistrationRequestDTOMapper userRegistrationRequestDTOMapper;
    private final LoginRequestDTOMapper loginRequestDTOMapper;

    /**
     * Handles the registration of a new user. Converts the incoming
     * {@link UserRegisterRequestDTO} to a domain-level {@link org.aueb.fair.dice.domain.user.User}
     * and delegates the save operation to the {@link UserCommandService}.
     *
     * @param request the validated registration request body
     * @return HTTP 200 OK if registration was successful
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(final @Valid @RequestBody UserRegisterRequestDTO request) {
        var sanitizedRequest = InputSanitizer.sanitize(request);
        var mappedReq = this.userRegistrationRequestDTOMapper.mapFromDTO(sanitizedRequest);
        userCommandPort.register(mappedReq);
        return ResponseEntity.ok().build();
    }

    /**
     * Handles user login. Validates credentials, generates a JWT if valid,
     * and returns it wrapped in a {@link JwtResponse}.
     *
     * @param request the validated login request body
     * @return HTTP 200 OK with a JWT token in the response body
     */
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(final @Valid @RequestBody LoginRequestDTO request) {
        var sanitizedRequest = InputSanitizer.sanitize(request);
        var token = userQueryPort.login(loginRequestDTOMapper.mapFromDTO(sanitizedRequest));
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
