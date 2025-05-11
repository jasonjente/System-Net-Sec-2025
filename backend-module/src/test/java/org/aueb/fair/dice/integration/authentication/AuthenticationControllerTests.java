package org.aueb.fair.dice.integration.authentication;


import org.aueb.fair.dice.adapter.primary.web.controller.AuthenticationController;
import org.aueb.fair.dice.integration.BaseIntegrationTests;
import org.aueb.fair.dice.web.dto.JwtResponse;
import org.aueb.fair.dice.web.dto.LoginRequestDTO;
import org.aueb.fair.dice.web.dto.UserRegisterRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link AuthenticationController}.
 */
@ActiveProfiles("test")
class AuthenticationControllerTests extends BaseIntegrationTests {

    private static final String SECURE_PASSWORD = "S3cureP@ss!";

    @Test
    void register_thenLogin_shouldReturnJwtToken() {
        // Register new user
        var registerDto = new UserRegisterRequestDTO("Test", "User", "testuser", "S3cureP@ss!");
        var registerResponse = restTemplate.postForEntity(
                getBaseUrl("/api/auth/register"), registerDto, Void.class);

        assertThat(registerResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Login with same user
        var loginDto = new LoginRequestDTO("testuser", "S3cureP@ss!");
        var loginResponse = restTemplate.postForEntity(
                getBaseUrl("/api/auth/login"), loginDto, JwtResponse.class);

        assertThat(loginResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(loginResponse.getBody()).isNotNull();
        assertThat(loginResponse.getBody().token()).isNotBlank();
    }

    @Test
    void login_withInvalidPassword_shouldReturnForbidden() {
        // Register valid user
        var dto = new UserRegisterRequestDTO("Bad", "Login", "wrongpass", SECURE_PASSWORD);
        restTemplate.postForEntity(getBaseUrl("/api/auth/register"), dto, Void.class);

        // Try invalid login
        var badLogin = new LoginRequestDTO("wrongpass", "badpass");
        var request = new HttpEntity<>(badLogin);
        var response = restTemplate.exchange(
                getBaseUrl("/api/auth/login"),
                HttpMethod.POST,
                request,
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
