package org.aueb.fair.dice.infrastructure.adapter.primary.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user registration requests.
 * Carries required user data for registration and includes validation and JSON control.
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRegisterRequestDTO {

    @NotBlank(message = "First name is required")
    @Size(max = 100, message = "First name must be at most 100 characters")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\-\\s']+$", message = "First name contains invalid characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\-\\s']+$", message = "Last name contains invalid characters")
    private String lastName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_.-]+$", message = "Username must contain only letters, numbers, and ._-")

    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 24, message = "Password must be 8 to 24 characters long")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&()_+\\-={}:;\"',.<>])[A-Za-z\\d@$!%*?&()_+\\-={}:;\"',.<>]{8,24}$",
            message = "Password must include uppercase, lowercase, digit, and special character"
    )
    private String password;

    @NotBlank
    @Email(message = "Invalid email address format")
    @Size(max = 254, message = "Email must not exceed 254 characters")
    private String email;
}
