package org.aueb.fair.dice.configuration.security.jwt;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

/**
 * Filter that intercepts requests validate the JWT token.
 *  This filter is registered in SecurityConfig to run before the actual controller
 *  request handling. It ensures that by the time a secured endpoint is reached,
 *  the user is already authenticated based on the JWT.
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    //TODO Add doFilterInternal Logic
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        filterChain.doFilter(request, response);
    }
}
