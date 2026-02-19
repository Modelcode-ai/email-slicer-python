package com.emailslicer;

/**
 * Utility class for parsing a validated email address into its username and domain components.
 *
 * <p>This class assumes the email has already been validated by {@link EmailValidator}.
 * It uses simple string operations to split the email on the '{@literal @}' character,
 * replicating the behavior of the original Python Email Slicer tool.
 */
public final class EmailParser {

    private EmailParser() {
        // utility class — prevent instantiation
    }

    /**
     * Parses a validated email address into username and domain parts.
     *
     * @param email a validated email address (must have passed {@link EmailValidator#isValid})
     * @return an {@link EmailParts} instance containing the username and domain
     */
    public static EmailParts parse(String email) {
        String trimmed = email.strip();
        int atIndex = trimmed.indexOf('@');

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Immutable value object that holds the parsed components of an email address.
     */
    public static final class EmailParts {

        private final String username;
        private final String domain;

        /**
         * Constructs a new {@code EmailParts} instance.
         *
         * @param username the part of the email before the '{@literal @}' symbol
         * @param domain   the part of the email after the '{@literal @}' symbol
         */
        public EmailParts(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        /**
         * Returns the username component of the email address.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the domain component of the email address.
         *
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }
    }
}
