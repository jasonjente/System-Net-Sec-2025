package org.aueb.fair.dice.configuration.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aueb.fair.dice.domain.user.User;
import org.aueb.fair.dice.application.port.secondary.user.UserPersistencePort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserPersistencePort userPersistencePort;


    public JwtAuthenticationFilter(JwtService jwtService, UserPersistencePort userPersistencePort) {
        this.jwtService = jwtService;
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = authHeader.substring(7);

        username = jwtService.extractUsername(token);
        if (username == null || SecurityContextHolder.getContext().getAuthentication() != null) {
            filterChain.doFilter(request, response);
            return;
        }

        userPersistencePort.findByUsername(username).ifPresent(user -> {
            if (jwtService.isTokenValid(token, user)) {
                var authToken = new UsernamePasswordAuthenticationToken(
                        user, null, null // no authorities for now
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        });

        filterChain.doFilter(request, response);
    }
}