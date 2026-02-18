package com.emailslicer;

/**
 * Core email parsing and validation logic.
 * This class provides functionality to parse email addresses into username and domain components
 * using basic string operations (indexOf, lastIndexOf, substring) to mirror Python's slicing semantics.
 */
public class EmailParser {

    /**
     * Parses an email address into username and domain components.
     * This method performs enhanced validation compared to the original Python implementation:
     * - Checks for null input
     * - Trims whitespace
     * - Ensures exactly one '@' character is present
     * - Validates non-empty username (text before '@')
     * - Validates non-empty domain (text after '@')
     *
     * @param rawInput the raw email string to parse (may contain leading/trailing whitespace)
     * @return EmailResult containing the parsed username and domain
     * @throws EmailValidationException if the email fails any validation check
     */
    public EmailResult parse(String rawInput) {
        // Null check
        if (rawInput == null) {
            throw new EmailValidationException("Email must not be null");
        }

        // Trim whitespace to match Python's strip() behavior
        String email = rawInput.trim();

        // Empty check after trimming
        if (email.isEmpty()) {
            throw new EmailValidationException("Email must not be empty");
        }

        // Find the first occurrence of '@'
        int atIndex = email.indexOf('@');
        if (atIndex < 0) {
            throw new EmailValidationException("Email must contain '@'");
        }

        // Verify there's only one '@' by comparing first and last index
        if (atIndex != email.lastIndexOf('@')) {
            throw new EmailValidationException("Email must contain exactly one '@'");
        }

        // Check for empty username (@ at the beginning)
        if (atIndex == 0) {
            throw new EmailValidationException("Email must have text before '@'");
        }

        // Check for empty domain (@ at the end)
        if (atIndex == email.length() - 1) {
            throw new EmailValidationException("Email must have text after '@'");
        }

        // Extract username and domain using substring operations
        // This mirrors Python's slicing: email[:atIndex] and email[atIndex+1:]
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailResult(username, domain);
    }
}
