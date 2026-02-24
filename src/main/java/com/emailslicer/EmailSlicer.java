package com.emailslicer;

/**
 * Core parsing logic for slicing an email address into username and domain parts.
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawInput the raw email address string (may contain leading/trailing whitespace)
     * @return an {@link EmailParts} instance containing the username and domain
     * @throws IllegalArgumentException if the input does not contain an '@' character
     */
    public static EmailParts slice(String rawInput) {
        String email = rawInput.strip();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Input does not contain an '@' character.");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Holds the parsed username and domain parts of an email address.
     */
    public static final class EmailParts {
        private final String username;
        private final String domain;

        public EmailParts(String username, String domain) {
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
