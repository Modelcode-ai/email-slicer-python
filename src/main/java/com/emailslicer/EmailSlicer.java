package com.emailslicer;

/**
 * Email Slicer - A simple tool that parses email addresses into username and domain components.
 */
public class EmailSlicer {

    /**
     * Parses an email address string into its username and domain components.
     *
     * @param rawInput the email address string to parse
     * @return EmailComponents containing the username and domain
     * @throws IllegalArgumentException if the input is invalid
     */
    public static EmailComponents parseEmail(String rawInput) {
        // Validate not null
        if (rawInput == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        // Normalize input by trimming whitespace
        String email = rawInput.trim();

        // Validate not empty
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        // Find the @ symbol
        int atIndex = email.indexOf('@');

        // Validate @ symbol presence and position
        // @ must not be at the start (position 0) or at the end (position length-1)
        if (atIndex <= 0 || atIndex >= email.length() - 1) {
            throw new IllegalArgumentException("Email must contain '@' with non-empty username and domain");
        }

        // Extract username (substring before @) and domain (substring after @)
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailComponents(username, domain);
    }
}
