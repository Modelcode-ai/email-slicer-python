package com.modelcode.emailslicer;

/**
 * Utility class that parses an email address string into its username and domain parts.
 * Validates the input and throws {@link IllegalArgumentException} on invalid email formats.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // prevent instantiation
    }

    /**
     * Parses a raw email string into an {@link EmailParts} instance.
     *
     * @param rawInput the email address string (may include leading/trailing whitespace)
     * @return an {@link EmailParts} containing the username and domain
     * @throws IllegalArgumentException if the input is null, empty, or not a valid email format
     */
    public static EmailParts parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Email must not be null.");
        }

        String email = rawInput.trim();
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty.");
        }

        int atFirst = email.indexOf('@');
        int atLast = email.lastIndexOf('@');

        if (atFirst < 0) {
            throw new IllegalArgumentException("Email must contain an '@' symbol.");
        }

        if (atFirst != atLast) {
            throw new IllegalArgumentException("Email must contain exactly one '@' symbol.");
        }

        if (atFirst == 0) {
            throw new IllegalArgumentException("Username must not be empty.");
        }

        if (atFirst == email.length() - 1) {
            throw new IllegalArgumentException("Domain must not be empty.");
        }

        String username = email.substring(0, atFirst);
        String domain = email.substring(atFirst + 1);

        return new EmailParts(username, domain);
    }
}
