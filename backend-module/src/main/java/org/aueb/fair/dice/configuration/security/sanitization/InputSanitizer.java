package org.aueb.fair.dice.configuration.security.sanitization;

import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.LoginRequestDTO;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.UserRegisterRequestDTO;

public class InputSanitizer {

    public static String sanitize(final String input) {
        if (input == null) return null;

        return input
                .trim()
                // removeS control characters except line breaks and tabs
                .replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "")
                // removes invisible characters (format, private-use, etc.)
                .replaceAll("\\p{C}", "")
                // collapse multiple whitespace to single space
                .replaceAll("\\s{2,}", " ");
    }

    public static UserRegisterRequestDTO sanitize(final UserRegisterRequestDTO dto) {
        dto.setUsername(sanitize(dto.getUsername()));
        dto.setFirstName(sanitize(dto.getFirstName()));
        dto.setLastName(sanitize(dto.getLastName()));
        dto.setPassword(sanitize(dto.getPassword()));
        dto.setEmail(sanitize(dto.getEmail()));
        return dto;
    }

    public static LoginRequestDTO sanitize(final LoginRequestDTO dto) {
        dto.setUsername(sanitize(dto.getUsername()));
        dto.setPassword(sanitize(dto.getPassword()));
        return dto;
    }

}
