package org.aueb.fair.dice.integration.password;

import org.aueb.fair.dice.integration.BaseIntegrationTests;
import org.aueb.fair.dice.infrastructure.adapter.primary.web.dto.UserRegisterRequestDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class PasswordValidationIntegrationTests extends BaseIntegrationTests {

    private String getRegisterUrl() {
        return UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/api/auth/register").toUriString();
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "short1!",              // too short
            "ALLUPPERCASE1!",       // no lowercase
            "alllowercase1!",       // no uppercase
            "NoDigits!",            // no digits
            "NoSpecialChar1",       // no special chars
            "ValidPass123!",        // ✅ valid
            "Another$Valid1"        // ✅ valid
    })
    void register_withVariousPasswords_shouldRespectPasswordPolicy(String password) {
        // Arrange
        var request = new UserRegisterRequestDTO(
                "Test",
                "User",
                "testuser" + password.hashCode(),
                password,
                "test@email.com"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<UserRegisterRequestDTO> entity = new HttpEntity<>(request, headers);

        // Act
        ResponseEntity<String> response = restTemplate.exchange(
                getRegisterUrl(),
                HttpMethod.POST,
                entity,
                String.class
        );

        // Assert
        if (password.equals("ValidPass123!") || password.equals("Another$Valid1")) {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        } else {
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        }
    }
}
