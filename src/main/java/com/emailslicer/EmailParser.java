package com.emailslicer;

/**
 * Parser for email addresses that validates and splits them into components.
 */
public class EmailParser {

    /**
     * Parses an email address into username and domain components.
     *
     * @param email the email address to parse
     * @return EmailParts containing the username and domain
     * @throws IllegalArgumentException if the email is null, empty,
     *         or does not meet validation requirements
     */
    public EmailParts parseEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex < 0) {
            throw new IllegalArgumentException("Email must contain '@'");
        }

        int secondAt = trimmed.indexOf('@', atIndex + 1);
        if (secondAt != -1) {
            throw new IllegalArgumentException(
                    "Email must contain only one '@'");
        }

        if (atIndex == 0 || atIndex == trimmed.length() - 1) {
            throw new IllegalArgumentException(
                    "'@' cannot be first or last character");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
