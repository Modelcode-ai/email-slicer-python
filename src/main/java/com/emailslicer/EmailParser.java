package com.emailslicer;

/**
 * Parses email addresses into username and domain components.
 * Implements the same logic as the Python emailSlicer.py script.
 */
public final class EmailParser {

    /**
     * Parses an email address into its username and domain components.
     *
     * @param input the email address to parse (may include leading/trailing whitespace)
     * @return an EmailComponents record containing the username and domain
     * @throws IllegalArgumentException if the input is null or does not contain an @ symbol
     */
    public EmailComponents parse(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Email input cannot be null");
        }

        String trimmed = input.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an @ symbol");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailComponents(username, domain);
    }
}
