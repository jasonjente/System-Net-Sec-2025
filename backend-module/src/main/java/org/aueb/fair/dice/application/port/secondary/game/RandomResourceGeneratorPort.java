package org.aueb.fair.dice.application.port.secondary.game;

/**
 * Secondary port for providing secure random numbers to the application core.
 * Kept generic so different bounds or distributions can be supported.
 */
public interface RandomResourceGeneratorPort {

    /**
     * Generate a random integer between 0 (inclusive) and bound (exclusive).
     *
     * @param bound upper bound (exclusive)
     * @return random int in [0, bound)
     */
    int nextInt(int bound);

    /**
     * Generate a random string.
     *
     * @return a random string char sequence.
     */
    String nextString();
}
