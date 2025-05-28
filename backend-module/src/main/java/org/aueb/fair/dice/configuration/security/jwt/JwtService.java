package org.aueb.fair.dice.configuration.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.aueb.fair.dice.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

/**
 * Service responsible for issuing and validating JWT tokens used for authentication
 * in the Fair Digital Dice game backend.
 *
 * <p>
 * The service uses a symmetric HMAC-based secret key (HS256) loaded from configuration.
 * Tokens contain standard claims such as subject, issuer, audience, issue date,
 * expiration date, and a unique identifier (JTI).
 * </p>
 *
 * <p>
 * The secret key is expected to be a Base64-encoded string with at least 256-bit entropy.
 * It should be defined in the application's {@code application.yml} under the key {@code jwt.secret}.
 * </p>
 */
@Service
public class JwtService {

    /**
     * Base64-encoded secret key loaded from application properties.
     * Must be at least 32 bytes (256 bits) for HS256 to be secure.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * The decoded and initialized signing key used for both token creation and validation.
     */
    private SecretKey secretKey;

    /**
     * Initializes the secret key after dependency injection.
     * Decodes the Base64-encoded secret and builds a JJWT-compatible signing key.
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    /**
     * Generates a signed JWT token containing the username as subject,
     * issued and expiration timestamps, issuer and audience, and a random JTI.
     *
     * @param user the authenticated user for whom the token is generated
     * @return a compact JWT token as a String
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuer("fair-dice-backend")
                .audience().add("fair-dice-frontend").and()
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .id(UUID.randomUUID().toString())
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validates a given JWT token by verifying its signature, expiration,
     * and matching the username in the subject claim.
     *
     * @param token the raw JWT token string received from the client
     * @param user  the expected user object to compare against the token's subject
     * @return {@code true} if the token is valid and matches the expected user, {@code false} otherwise
     */
    public boolean isTokenValid(String token, User user) {
        try {
            var claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject().equals(user.getUsername())
                    && claims.getExpiration().after(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Extracts the username (subject) from the JWT token.
     *
     * @param token the JWT token string
     * @return the username embedded in the token, or null if parsing fails
     */
    public String extractUsername(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }


}
