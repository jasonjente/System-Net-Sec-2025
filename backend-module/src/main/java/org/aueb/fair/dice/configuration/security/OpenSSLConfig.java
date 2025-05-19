package org.aueb.fair.dice.configuration.security;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class that sets up an additional HTTP connector
 * to redirect all HTTP traffic (port 8080) to HTTPS (port 8443).
 *
 * <p>This is useful for enforcing secure connections via automatic redirection.</p>
 */
@Configuration
public class OpenSSLConfig {

    /**
     * Creates a customized {@link ServletWebServerFactory} that includes
     * a redirecting HTTP connector to HTTPS.
     *
     * @return a Tomcat web server factory with HTTP-to-HTTPS redirection
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    /**
     * Defines the HTTP connector that listens on port 8080 and redirects to 8443.
     *
     * @return the configured {@link Connector} instance
     */
    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
}
