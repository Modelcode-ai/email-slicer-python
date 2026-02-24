package com.emailslicer;

/**
 * Encapsulates email parsing and validation logic.
 *
 * <p>Trims the input, validates the presence of an {@code @} character,
 * and splits the email into username and domain components at the first
 * {@code @} occurrence.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class; prevent instantiation.
    }

    /**
     * Parses a raw email string into its username and domain components.
     *
     * <p>The input is trimmed of leading and trailing whitespace before
     * validation. An {@code @} character must be present; splitting occurs
     * at the first {@code @}. Empty username or domain segments are
     * considered valid as long as the {@code @} character exists.</p>
     *
     * @param rawEmail the email address to parse
     * @return a {@link ParsedEmail} containing the username and domain
     * @throws IllegalArgumentException if the input is null, empty after
     *         trimming, or does not contain an {@code @} character
     */
    public static ParsedEmail parse(String rawEmail) {
        if (rawEmail == null || rawEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid email address: input is empty.");
        }

        String trimmed = rawEmail.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email address: missing '@' character.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Immutable value object representing a parsed email address.
     */
    public static final class ParsedEmail {

        private final String username;
        private final String domain;

        public ParsedEmail(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        /** Returns the portion of the email before the {@code @} character. */
        public String getUsername() {
            return username;
        }

        /** Returns the portion of the email after the {@code @} character. */
        public String getDomain() {
            return domain;
        }
    }
}
