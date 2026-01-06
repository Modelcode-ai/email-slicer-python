package com.example.emailslicer;

public final class EmailParser {

    private EmailParser() {
        // utility class, prevent instantiation
    }

    /**
     * Parses an email address into username and domain.
     *
     * @param email raw email string (may be null or blank)
     * @return EmailResult if parsing is successful
     * @throws IllegalArgumentException if the email is invalid
     */
    public static EmailResult parse(final String email) {
        if (email == null) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        String trimmed = email.strip();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        int atIndex = trimmed.indexOf('@');

        // Minimal validation: @ must exist and cannot be
        // first or last character.
        if (atIndex <= 0 || atIndex == trimmed.length() - 1) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailResult(username, domain);
    }
}
