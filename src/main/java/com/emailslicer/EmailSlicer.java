package com.emailslicer;

/**
 * Core parsing and validation logic for email addresses.
 * Splits a raw email string into its username and domain components.
 */
public class EmailSlicer {

    /**
     * Parses a raw email input string into its username and domain parts.
     *
     * @param rawInput the raw email string (may include leading/trailing whitespace)
     * @return an {@link EmailComponents} record with the parsed username and domain
     * @throws IllegalArgumentException if the input is null, blank, missing '@',
     *                                  contains multiple '@' characters, or has an
     *                                  empty username or domain
     */
    public EmailComponents parse(String rawInput) {
        // Placeholder — implementation in a subsequent task.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
