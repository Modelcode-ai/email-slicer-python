package com.emailslicer;

/**
 * Parses email addresses into username and domain components.
 * <p>
 * Validation rules:
 * <ul>
 *   <li>An email is valid if it contains at least one '@' character</li>
 *   <li>The username is the substring before the first '@'</li>
 *   <li>The domain is the substring after the first '@'</li>
 *   <li>Empty username or domain is allowed</li>
 *   <li>Multiple '@' characters are allowed (splits on first '@')</li>
 * </ul>
 */
public final class EmailParser {

    private EmailParser() {
        // Utility class - prevent instantiation
    }

    /**
     * Parses an email address into its username and domain components.
     *
     * @param email the email address to parse (should be trimmed)
     * @return a ParsedEmail containing the username and domain
     * @throws IllegalArgumentException if email has no '@' character
     */
    public static ParsedEmail parse(final String email) {
        if (email == null || email.indexOf('@') == -1) {
            throw new IllegalArgumentException(
                    "Email must contain an '@' character");
        }

        int atIndex = email.indexOf('@');
        String user = email.substring(0, atIndex);
        String dom = email.substring(atIndex + 1);

        return new ParsedEmail(user, dom);
    }

    /**
     * Represents a parsed email address with username and domain components.
     */
    public static final class ParsedEmail {
        /** The username part of the email (before the '@'). */
        private final String username;
        /** The domain part of the email (after the '@'). */
        private final String domain;

        /**
         * Constructs a ParsedEmail with the given username and domain.
         *
         * @param user the username part of the email
         * @param dom the domain part of the email
         */
        public ParsedEmail(final String user, final String dom) {
            this.username = user;
            this.domain = dom;
        }

        /**
         * Returns the username part of the email.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the domain part of the email.
         *
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }
    }
}
