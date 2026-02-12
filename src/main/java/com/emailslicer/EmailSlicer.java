package com.emailslicer;

/**
 * Core email parsing logic that splits an email address into username and domain.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class - prevent instantiation
    }

    /**
     * Parses an email address and extracts the username and domain.
     *
     * @param rawInput the raw email input (may contain leading/trailing whitespace)
     * @return a ParsedEmail containing the username and domain
     * @throws IllegalArgumentException if the input is not a valid email
     *         (no @ symbol, or @ is at the first or last position)
     */
    public static ParsedEmail parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Email input cannot be null");
        }

        String email = rawInput.strip();

        int atIndex = email.indexOf('@');

        // Validate: @ must exist, not be at start (index 0), not be at end
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }
        if (atIndex == 0) {
            throw new IllegalArgumentException("Email cannot start with @");
        }
        if (atIndex == email.length() - 1) {
            throw new IllegalArgumentException("Email cannot end with @");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Immutable data class holding the parsed username and domain.
     */
    public static final class ParsedEmail {
        private final String username;
        private final String domain;

        public ParsedEmail(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }
}
