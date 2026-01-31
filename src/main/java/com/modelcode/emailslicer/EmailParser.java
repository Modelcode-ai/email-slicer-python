package com.modelcode.emailslicer;

/**
 * Core email parsing and validation logic.
 * Encapsulates all validation rules as a stateless pure function.
 */
public class EmailParser {

    /**
     * Parses an email address into username and domain components.
     *
     * @param email the raw email string to parse
     * @return EmailResult containing the username and domain
     * @throws IllegalArgumentException if the email is invalid
     */
    public EmailResult parse(String email) {
        // Validation rule 1: Input must not be null
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        // Validation rule 2: Trim whitespace and check for empty string
        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Validation rule 3: Email must contain exactly one @ character
        int firstAtIndex = trimmedEmail.indexOf('@');
        int lastAtIndex = trimmedEmail.lastIndexOf('@');

        if (firstAtIndex == -1) {
            throw new IllegalArgumentException("Email must contain @ symbol");
        }

        if (firstAtIndex != lastAtIndex) {
            throw new IllegalArgumentException("Email must contain exactly one @ symbol");
        }

        // Validation rule 4: Username (before @) must be non-empty
        if (firstAtIndex == 0) {
            throw new IllegalArgumentException("Username (before @) cannot be empty");
        }

        // Validation rule 5: Domain (after @) must be non-empty
        if (firstAtIndex == trimmedEmail.length() - 1) {
            throw new IllegalArgumentException("Domain (after @) cannot be empty");
        }

        // Extract username and domain
        String username = trimmedEmail.substring(0, firstAtIndex);
        String domain = trimmedEmail.substring(firstAtIndex + 1);

        return new EmailResult(username, domain);
    }
}
