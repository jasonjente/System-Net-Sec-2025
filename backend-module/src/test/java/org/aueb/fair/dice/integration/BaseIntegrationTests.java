package org.aueb.fair.dice.integration;

import org.aueb.fair.dice.integration.database.container.SharedPostgresContainer;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

/**
 * Abstract base class for integration tests in the Fair Dice backend.
 *
 * <p>
 * This class boots the Spring Boot application on a random port and provides
 * a preconfigured {@link TestRestTemplate} for making HTTP calls to the application.
 * </p>
 *
 * <p>
 * It also initializes a shared PostgreSQL Testcontainers instance using
 * {@link SharedPostgresContainer} to emulate a real database environment.
 * This ensures consistency with the production setup and allows database interactions
 * to be fully tested.
 * </p>
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTests {

    @LocalServerPort
    protected int port;

    protected TestRestTemplate restTemplate;

    private static final SharedPostgresContainer POSTGRES = SharedPostgresContainer.getInstance();

    /**
     * Registers dynamic PostgreSQL container properties into the Spring context
     * so that the application connects to the containerized DB instead of the default one.
     *
     * @param registry the dynamic property registry used by Spring Boot
     */
    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    /**
     * Initializes the {@link TestRestTemplate} with an HTTP client that supports
     * advanced methods like PATCH.
     */
    @BeforeEach
    public void setUp() {
        var factory = new HttpComponentsClientHttpRequestFactory();
        this.restTemplate = new TestRestTemplate();
        this.restTemplate.getRestTemplate().setRequestFactory(factory);
    }

    /**
     * Constructs the full base URL to the application server for the given path.
     *
     * @param path the URI path (e.g. "/api/auth/login")
     * @return the complete base URL for use in tests
     */
    protected String getBaseUrl(String path) {
        return "http://localhost:" + port + path;
    }
}
