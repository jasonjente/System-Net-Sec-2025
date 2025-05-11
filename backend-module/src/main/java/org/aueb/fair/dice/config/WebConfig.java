package org.aueb.fair.dice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Web MVC configuration for the Fair Dice application.
 *
 * <p>Registers request interceptors for additional behaviors like logging.</p>
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final AuditLoggingInterceptor auditLoggingInterceptor;

    /**
     * Adds interceptors to the web context.
     *
     * @param registry the interceptor registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditLoggingInterceptor)
                .addPathPatterns("/api/**");
    }
}
