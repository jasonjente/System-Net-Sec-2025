package org.aueb.fair.dice.application.port.secondary.security;

public interface HashingPort {

    /**
     * Hashes each provided argument using SHA-256 and returns
     * the concatinated hex digest.
     *
     * @param arguments one or more strings to hash
     * @return SHA-256 hex digests
     */
    String hashConcatenation(String... arguments);
}
