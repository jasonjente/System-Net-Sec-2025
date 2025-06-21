package org.aueb.fair.dice.infrastructure.adapter.secondary.dice;

import org.aueb.fair.dice.application.port.secondary.game.RandomResourceGeneratorPort;
import org.aueb.fair.dice.infrastructure.adapter.secondary.security.SHA256HashingService;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Adapter providing secure random numbers.
 * Instantiable; no DI framework or singleton needed.
 */
public class RandomResourceGeneratorService implements RandomResourceGeneratorPort {
    private final SecureRandom rng;

    public RandomResourceGeneratorService() {
        SecureRandom instance;
        try {
            instance = SecureRandom.getInstanceStrong();
        } catch (NoSuchAlgorithmException e) {
            instance = new SecureRandom();
        }
        this.rng = instance;
    }

    /**
     * Generates a random integer with a given upper bound.
     *
     * @param bound upper bound (exclusive).
     * @return the random integer.
     */
    @Override
    public int nextInt(int bound) {
        // origin is inclusive
        // bound is exclusive [origin, bound)
        return rng.nextInt(1, bound);
    }

    /**
     * Generate a random string.
     *
     * @return a random char sequence.
     */
    @Override
    public String nextString() {
        var hashingPort = new SHA256HashingService();
        long timestamp = System.currentTimeMillis();
        int salt1 = rng.nextInt(Integer.MAX_VALUE);
        int salt2 = rng.nextInt(Integer.MAX_VALUE);
        return hashingPort.hashConcatenation(String.valueOf(timestamp), String.valueOf(salt1),
                String.valueOf(salt2));
    }
}
