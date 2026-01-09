package com.emailslicer;

import java.util.Optional;

/**
 * Utility class for parsing and validating email addresses.
 *
 * This class implements a two-tier validation strategy:
 * 1. Basic structural checks (null, empty, single @, @ position)
 * 2. Format checks (domain must contain dot, ASCII-only characters)
 */
public final class EmailParser {

    /** Maximum ASCII code point value. */
    private static final int MAX_ASCII_VALUE = 127;

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailParser() {
        throw new UnsupportedOperationException(
                "Utility class cannot be instantiated");
    }

    /**
     * Parses and validates an email address.
     *
     * @param email the email address to parse (may be null)
     * @return an Optional containing EmailParts if valid,
     *         empty Optional if invalid
     */
    public static Optional<EmailParts> parse(final String email) {
        // Handle null input
        if (email == null) {
            return Optional.empty();
        }

        // Trim and check for empty
        String trimmed = email.trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        // Check for non-ASCII characters (code points > 127)
        if (!isAsciiOnly(trimmed)) {
            return Optional.empty();
        }

        // Check for exactly one @ character
        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty(); // No @ found
        }

        int lastAtIndex = trimmed.lastIndexOf('@');
        if (atIndex != lastAtIndex) {
            return Optional.empty(); // Multiple @ characters
        }

        // Check @ is not at the start or end
        if (atIndex == 0 || atIndex == trimmed.length() - 1) {
            return Optional.empty();
        }

        // Split into username and domain
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        // Check both parts are non-empty
        // (already ensured by @ position check, but explicit)
        if (username.isEmpty() || domain.isEmpty()) {
            return Optional.empty();
        }

        // Check for internal spaces
        if (username.contains(" ") || domain.contains(" ")) {
            return Optional.empty();
        }

        // Check domain contains at least one dot
        // that is not at start or end
        if (!hasValidDomainDot(domain)) {
            return Optional.empty();
        }

        return Optional.of(new EmailParts(username, domain));
    }

    /**
     * Checks if a string contains only ASCII characters.
     *
     * @param str the string to check
     * @return true if all characters are ASCII, false otherwise
     */
    private static boolean isAsciiOnly(final String str) {
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) > MAX_ASCII_VALUE) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a domain has at least one dot.
     *
     * The dot must not be at the start or end.
     *
     * @param domain the domain to check
     * @return true if domain has a valid dot, false otherwise
     */
    private static boolean hasValidDomainDot(final String domain) {
        // Check if domain starts or ends with a dot
        if (domain.startsWith(".") || domain.endsWith(".")) {
            return false;
        }

        // Check if domain contains at least one dot
        int dotIndex = domain.indexOf('.');
        if (dotIndex == -1) {
            return false; // No dot found
        }

        return true;
    }

    /**
     * Immutable data class representing the parsed parts of an email.
     */
    public static final class EmailParts {
        /** The username (local part) of the email address. */
        private final String username;
        /** The domain of the email address. */
        private final String domain;

        /**
         * Constructs an EmailParts instance.
         *
         * @param usernameParam the username (local part) of the email
         * @param domainParam the domain of the email
         */
        public EmailParts(final String usernameParam,
                          final String domainParam) {
            this.username = usernameParam;
            this.domain = domainParam;
        }

        /**
         * Gets the username (local part) of the email.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Gets the domain of the email.
         *
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }
    }
}
