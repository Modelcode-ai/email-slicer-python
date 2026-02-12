package com.emailslicer;

/**
 * Email parsing and validation logic.
 * Encapsulates the logic for validating email addresses and extracting
 * username and domain components.
 */
public final class EmailParser {

    /**
     * Result of parsing an email address.
     * Contains the validation status and, if valid, the username and domain.
     */
    public static class Result {
        private final boolean valid;
        private final String username;
        private final String domain;

        private Result(boolean valid, String username, String domain) {
            this.valid = valid;
            this.username = username;
            this.domain = domain;
        }

        public boolean isValid() {
            return valid;
        }

        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }

    private EmailParser() {
        // Private constructor to prevent instantiation
    }

    /**
     * Parses an email address string and returns the result.
     *
     * @param rawInput the raw email input (may be null or contain leading/trailing whitespace)
     * @return a Result containing validation status and parsed username/domain
     */
    public static Result parse(String rawInput) {
        // Handle null input
        if (rawInput == null) {
            return new Result(false, null, null);
        }

        // Trim whitespace to emulate Python's .strip()
        String trimmed = rawInput.trim();

        // Empty string after trim is invalid
        if (trimmed.isEmpty()) {
            return new Result(false, null, null);
        }

        // Find first occurrence of '@'
        int atIndex = trimmed.indexOf('@');

        // No '@' found means invalid email
        if (atIndex == -1) {
            return new Result(false, null, null);
        }

        // Split on first '@'
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new Result(true, username, domain);
    }
}
