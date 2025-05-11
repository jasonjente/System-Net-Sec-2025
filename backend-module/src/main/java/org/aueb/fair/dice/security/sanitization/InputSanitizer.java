package org.aueb.fair.dice.security.sanitization;

import org.aueb.fair.dice.web.dto.LoginRequestDTO;
import org.aueb.fair.dice.web.dto.UserRegisterRequestDTO;

public class InputSanitizer {

    public static String sanitize(final String input) {
        return input == null ? null : input.trim();
    }

    public static UserRegisterRequestDTO sanitize(final UserRegisterRequestDTO dto) {
        dto.setUsername(sanitize(dto.getUsername()));
        dto.setFirstName(sanitize(dto.getFirstName()));
        dto.setLastName(sanitize(dto.getLastName()));
        dto.setPassword(sanitize(dto.getPassword()));
        return dto;
    }

    public static LoginRequestDTO sanitize(final LoginRequestDTO dto) {
        dto.setUsername(sanitize(dto.getUsername()));
        dto.setPassword(sanitize(dto.getPassword()));
        return dto;
    }
}
