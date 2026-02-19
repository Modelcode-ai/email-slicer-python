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
        if (rawInput == null || rawInput.isBlank()) {
            throw new IllegalArgumentException("Email must not be null or blank");
        }

        String email = rawInput.strip();

        int firstAt = email.indexOf('@');
        int lastAt = email.lastIndexOf('@');

        if (firstAt == -1) {
            throw new IllegalArgumentException("Email must contain exactly one '@' character");
        }

        if (firstAt != lastAt) {
            throw new IllegalArgumentException("Email must contain exactly one '@' character");
        }

        String username = email.substring(0, firstAt);
        String domain = email.substring(firstAt + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username part must not be empty");
        }

        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain part must not be empty");
        }

        return new EmailComponents(username, domain);
    }
}
