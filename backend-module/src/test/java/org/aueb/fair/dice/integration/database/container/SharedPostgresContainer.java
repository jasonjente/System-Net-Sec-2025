package org.aueb.fair.dice.integration.database.container;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Singleton wrapper for a shared PostgreSQL Testcontainers instance.
 *
 * <p>
 * This class ensures that only one PostgreSQL container instance is created and reused across
 * multiple integration test classes. It avoids redundant startup overhead and prevents
 * connection conflicts caused by multiple parallel containers.
 * </p>
 *
 * <p>
 * The container is started when {@link #getInstance()} is first called and will remain running
 * until the JVM shuts down. The {@link #stop()} method is overridden to no-op to prevent
 * premature termination by the JUnit lifecycle.
 * </p>
 */
public class SharedPostgresContainer extends PostgreSQLContainer<SharedPostgresContainer> {

    private static final String IMAGE_VERSION = "postgres:16";
    private static SharedPostgresContainer container;

    private SharedPostgresContainer() {
        super(IMAGE_VERSION);
        withDatabaseName("gdpr");
        withUsername("user");
        withPassword("password");
    }

    /**
     * Returns the singleton instance of the shared PostgreSQL container.
     * If it hasn't been initialized yet, it is created and started.
     *
     * @return the shared container instance
     */
    public static SharedPostgresContainer getInstance() {
        if (container == null) {
            container = new SharedPostgresContainer();
            container.start();
        }
        return container;
    }

    /**
     * Overrides the default stop behavior to prevent stopping the container explicitly.
     * The container will be shut down automatically on JVM exit.
     */
    @Override
    public void stop() {
        // do nothing, JVM shutdown hook handles it
    }
}
