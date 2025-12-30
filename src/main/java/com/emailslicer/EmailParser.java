package com.emailslicer;

/**
 * Parses email addresses into username and domain components.
 *
 * <p>This parser implements the same semantics as the original
 * Python email slicer:
 * <ul>
 *   <li>Splits on the first {@code @} character only</li>
 *   <li>Empty username is allowed (e.g., {@code @domain.com})</li>
 *   <li>Empty domain is allowed (e.g., {@code user@})</li>
 *   <li>Multiple {@code @} characters are allowed</li>
 * </ul>
 */
public final class EmailParser {

    private EmailParser() {
        // Utility class - prevent instantiation
    }

    /**
     * Parses an email address into username and domain components.
     *
     * <p>The input should already be trimmed before calling this method.
     *
     * @param email the email address to parse
     * @return a {@link ParsedEmail} containing the username and domain
     * @throws IllegalArgumentException if email has no {@code @} character
     */
    public static ParsedEmail parse(final String email) {
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException(
                "Email must contain an '@' character");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Represents a parsed email with username and domain components.
     */
    public static final class ParsedEmail {
        /** The username part of the email (before the @). */
        private final String username;
        /** The domain part of the email (after the @). */
        private final String domain;

        /**
         * Constructs a new ParsedEmail.
         *
         * @param theUsername the username part of the email
         * @param theDomain   the domain part of the email
         */
        public ParsedEmail(final String theUsername, final String theDomain) {
            this.username = theUsername;
            this.domain = theDomain;
        }

        /**
         * Returns the username part of the email address.
         *
         * @return the username (the part before the @)
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the domain part of the email address.
         *
         * @return the domain (the part after the @)
         */
        public String getDomain() {
            return domain;
        }
    }
}
