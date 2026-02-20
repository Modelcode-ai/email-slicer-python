package com.example.emailslicer;

/**
 * Utility class for parsing email addresses into their username and domain components.
 *
 * <p>This class provides a static {@link #parse(String)} method that validates a raw
 * email string and extracts the username (portion before {@code @}) and domain
 * (portion after {@code @}).</p>
 *
 * <p>Migrated from the original Python {@code emailSlicer.py} with stricter validation:
 * empty input, missing {@code @}, {@code @} at start/end, and multiple {@code @}
 * characters are all rejected.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Parses a raw email string into its username and domain components.
     *
     * <p>The input is trimmed of leading and trailing whitespace before validation.
     * The method enforces the following rules:</p>
     * <ul>
     *   <li>The input must not be null, empty, or blank after trimming.</li>
     *   <li>The input must contain exactly one {@code @} character.</li>
     *   <li>The {@code @} must not be the first or last character (i.e., both
     *       username and domain must be non-empty).</li>
     * </ul>
     *
     * @param rawInput the raw email address string (may include leading/trailing whitespace)
     * @return a {@link ParsedEmail} containing the extracted username and domain
     * @throws IllegalArgumentException if the input is null, blank, or not a valid email format
     */
    public static ParsedEmail parse(String rawInput) {
        System.out.println("[TRACE] Parsing email input...");
        if (rawInput == null) {
            throw new IllegalArgumentException("email must not be null");
        }

        String trimmed = rawInput.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("email must not be empty or blank");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("email must contain an '@' character");
        }

        // Ensure exactly one '@' by comparing indexOf and lastIndexOf
        if (atIndex != trimmed.lastIndexOf('@')) {
            throw new IllegalArgumentException("email must contain exactly one '@' character");
        }

        if (atIndex == 0) {
            throw new IllegalArgumentException("email must have a non-empty username before '@'");
        }

        if (atIndex == trimmed.length() - 1) {
            throw new IllegalArgumentException("email must have a non-empty domain after '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Convenience method to extract just the username from a raw email string.
     *
     * @param rawInput the raw email address string
     * @return the username portion of the email
     * @throws IllegalArgumentException if the input is invalid
     */
    public static String getUsername(String rawInput) {
        return parse(rawInput).getUsername();
    }

    /**
     * Convenience method to extract just the domain from a raw email string.
     *
     * @param rawInput the raw email address string
     * @return the domain portion of the email
     * @throws IllegalArgumentException if the input is invalid
     */
    public static String getDomain(String rawInput) {
        return parse(rawInput).getDomain();
    }

    /**
     * Immutable value object holding the parsed components of an email address.
     */
    public static final class ParsedEmail {

        private final String username;
        private final String domain;

        /**
         * Constructs a new {@code ParsedEmail} with the given username and domain.
         *
         * @param username the portion of the email before {@code @}
         * @param domain   the portion of the email after {@code @}
         */
        public ParsedEmail(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        /**
         * Returns the username portion of the email address.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the domain portion of the email address.
         *
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }

        @Override
        public String toString() {
            return username + "@" + domain;
        }
    }
}
