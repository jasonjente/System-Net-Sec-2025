package org.aueb.fair.dice.configuration.audit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor that logs incoming HTTP requests for audit purposes.
 *
 * <p>Logs HTTP method, URI, and the client IP address.</p>
 */
@Component
@Slf4j
public class AuditLoggingInterceptor implements HandlerInterceptor {

    /**
     * Called before the controller method is executed.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response object
     * @param handler  the matched handler method
     * @return true to continue processing the request
     */
    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response,
                             final Object handler) {
        log.info("Received HTTP {} request, on resource {} from the IP Address {}",
                request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
        return true;
    }
}
