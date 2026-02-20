package com.emailslicer;

/**
 * Core email parsing and validation logic.
 *
 * <p>Takes a raw email string, trims whitespace, validates the structure,
 * and splits it into username and domain components. This class is free
 * of I/O side effects and is designed for easy unit testing.</p>
 */
public class EmailSlicer {

    /**
     * Parses an email address string into its username and domain parts.
     *
     * <p>The input is trimmed of leading and trailing whitespace before
     * validation. A valid email must contain exactly one {@code @} character
     * with at least one character on each side.</p>
     *
     * @param email the raw email address string to parse
     * @return an {@link EmailParts} record containing the username and domain
     * @throws IllegalArgumentException if the input is null or not a valid email address
     */
    public EmailParts parse(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Invalid email address: null");
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');

        // Must contain '@'
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email address: " + trimmed);
        }

        // Must contain exactly one '@'
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            throw new IllegalArgumentException("Invalid email address: " + trimmed);
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        // Username must not be empty
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address: " + trimmed);
        }

        // Domain must not be empty
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address: " + trimmed);
        }

        return new EmailParts(username, domain);
    }
}
