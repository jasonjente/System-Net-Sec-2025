package org.aueb.fair.dice.domain.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationErrorCollector {

    /**
     * A list that holds all validation error messages.
     */
    private final List<String> errors = new ArrayList<>();

    /**
     * Adds a new error message to the collector.
     *
     * @param message the error message to add. Must not be {@code null}.
     */
    public void addMessage(String message) {
        if (message != null) {
            errors.add(message);
        }
    }

    /**
     * Returns all collected error messages as a single comma-separated string.
     *
     * @return a string of error messages separated by commas, or an empty string if no errors exist
     */
    public String printMessages() {
        return String.join(", ", errors);
    }

    /**
     * Checks whether any validation errors have been collected.
     *
     * @return {@code true} if one or more error messages exist, {@code false} otherwise
     */
    public boolean hasErrors() {
        return !errors.isEmpty();
    }
}
