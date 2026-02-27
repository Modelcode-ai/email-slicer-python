package com.emailslicer;

/**
 * Core email parsing logic.
 * <p>
 * Accepts a raw email string, trims whitespace, validates the presence and
 * position of the {@code @} symbol, and splits the input into username and
 * domain components on the first {@code @}.
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawInputEmail the raw email string (may include leading/trailing whitespace)
     * @return an {@link EmailParts} record containing the parsed username and domain
     * @throws IllegalArgumentException if the input is null, empty, or does not
     *                                  contain a valid {@code @} placement
     */
    public EmailParts parse(String rawInputEmail) {
        if (rawInputEmail == null) {
            throw new IllegalArgumentException("Email input must not be null");
        }

        var trimmed = rawInputEmail.trim();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email input must not be empty");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an '@' symbol");
        }

        if (atIndex == 0) {
            throw new IllegalArgumentException("Email must have a username before '@'");
        }

        if (atIndex == trimmed.length() - 1) {
            throw new IllegalArgumentException("Email must have a domain after '@'");
        }

        var username = trimmed.substring(0, atIndex);
        var domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
