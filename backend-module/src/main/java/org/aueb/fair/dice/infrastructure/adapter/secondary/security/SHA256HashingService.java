package org.aueb.fair.dice.infrastructure.adapter.secondary.security;

import org.aueb.fair.dice.application.port.secondary.security.HashingPort;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA256HashingService implements HashingPort {

    private static final String ALGORITHM = "SHA-256";

    @Override
    public String hashConcatenation(String... arguments) {
        StringBuilder sb = new StringBuilder();
        for (String arg : arguments) {
            sb.append(arg);
        }
        return hash(sb.toString());
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);
            byte[] digest = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder(digest.length * 2);
            for (byte b : digest) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-256 not available", e);
        }
    }
}
