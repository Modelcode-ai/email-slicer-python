package com.emailslicer;

/**
 * Core email parsing logic that extracts username and domain from email addresses.
 * This class is free of I/O operations and focuses solely on validation and parsing.
 */
public class EmailSlicer {

    /**
     * Parses an email address and extracts the username and domain parts.
     * Uses the first occurrence of '@' as the separator, matching the behavior
     * of Python's index() method used in the original implementation.
     *
     * @param rawInput the raw email input string (may include leading/trailing whitespace)
     * @return EmailParts containing the username and domain
     * @throws IllegalArgumentException if the input is invalid (null, empty, no @,
     *         empty username, or empty domain)
     */
    public static EmailParts parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Email input cannot be null");
        }

        String email = rawInput.trim();

        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email input cannot be empty");
        }

        int atIndex = email.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }

        if (atIndex == 0) {
            throw new IllegalArgumentException("Email username cannot be empty");
        }

        if (atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Email domain cannot be empty");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Immutable container for parsed email parts.
     * Uses Java 17 record for conciseness and automatic implementation of
     * equals(), hashCode(), and toString().
     */
    public record EmailParts(String username, String domain) {
        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }
}
