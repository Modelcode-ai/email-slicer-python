package com.emailslicer;

/**
 * Core email validation and parsing logic.
 *
 * This class provides functionality to validate and parse email addresses,
 * extracting the username and domain components.
 */
public final class EmailSlicer {

    /**
     * Validates and parses an email address into its username and domain components.
     *
     * The method trims whitespace from the input, validates that it contains an @ symbol,
     * and splits on the first @ character (matching Python's email.index("@") behavior).
     *
     * @param email the raw email input string
     * @return an EmailParts record containing the username and domain
     * @throws IllegalArgumentException if the email does not contain an @ symbol
     */
    public EmailParts slice(String email) {
        String trimmed = email.strip();

        int atIndex = trimmed.indexOf("@");
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an @ symbol");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
