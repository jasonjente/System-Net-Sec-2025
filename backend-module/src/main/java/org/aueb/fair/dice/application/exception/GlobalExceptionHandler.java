package org.aueb.fair.dice.application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler for the application.
 *
 * <p>Intercepts known exception types and formats them into consistent JSON responses.</p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles validation errors for invalid password credentials in login requests.
     *
     * @param ex the validation exception thrown by Spring
     * @return a 403 FORBIDDEN response with field-level error details
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid username or password");
    }
    /**
     * Handles validation errors for @Valid annotated request bodies.
     *
     * @param ex the validation exception thrown by Spring
     * @return a 400 BAD REQUEST response with field-level error details
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation failed");
        body.put("details", ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage()).toList());

        return ResponseEntity.badRequest().body(body);
    }

    /**
     * Handles uncaught generic exceptions.
     *
     * @param ex the exception that was thrown
     * @return a 500 INTERNAL SERVER ERROR with basic info
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneric(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        body.put("error", "Unexpected error");
        body.put("message", ex.getMessage());
        return ResponseEntity.internalServerError().body(body);
    }
}
