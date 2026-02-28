package com.emailslicer;

/**
 * Pure business logic for parsing an email address into its username and domain
 * components. This class contains no console I/O and is deterministic and
 * side-effect free.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Parses a raw email input string into its username and domain components.
     *
     * <p>The input is trimmed of leading and trailing whitespace. The email is
     * split on the <b>first</b> {@code @} character: everything before it becomes
     * the username, everything after it becomes the domain.
     *
     * @param rawInput the raw email string (may include leading/trailing whitespace)
     * @return a {@link ParsedEmail} containing the extracted username and domain
     * @throws IllegalArgumentException if the input is null, empty, whitespace-only,
     *                                  or does not contain an {@code @} character
     */
    public static ParsedEmail parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Input must not be null");
        }

        String trimmed = rawInput.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Input must not be empty");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Input does not contain '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Immutable value type holding the parsed username and domain of an email address.
     */
    public static final class ParsedEmail {

        private final String username;
        private final String domain;

        /**
         * Creates a new {@code ParsedEmail} with the given username and domain.
         *
         * @param username the part of the email before the {@code @}
         * @param domain   the part of the email after the {@code @}
         */
        public ParsedEmail(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        /** Returns the username portion of the email (before the {@code @}). */
        public String getUsername() {
            return username;
        }

        /** Returns the domain portion of the email (after the {@code @}). */
        public String getDomain() {
            return domain;
        }
    }
}
