package com.emailslicer.validator;

/**
 * Validates and normalizes email addresses.
 * Provides enhanced validation beyond basic '@' presence,
 * rejecting malformed emails such as those with empty
 * username or domain parts.
 */
public class EmailValidator {

    /**
     * Validates an email address and returns the normalized
     * (trimmed) email.
     *
     * @param email the email address to validate
     * @return the trimmed email address if valid
     * @throws InvalidEmailException if the email is invalid,
     *         with a specific error message
     */
    public String validate(final String email) {
        // Check for null input
        if (email == null) {
            throw new InvalidEmailException("Email address must not be null.");
        }

        // Trim whitespace and check for empty string
        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) {
            throw new InvalidEmailException("Email address must not be empty.");
        }

        // Validate exactly one '@' symbol
        int firstAtIndex = trimmedEmail.indexOf('@');
        int lastAtIndex = trimmedEmail.lastIndexOf('@');

        if (firstAtIndex == -1) {
            throw new InvalidEmailException(
                    "Email address must contain exactly one '@' symbol.");
        }

        if (firstAtIndex != lastAtIndex) {
            throw new InvalidEmailException(
                    "Email address must contain exactly one '@' symbol.");
        }

        // Extract username and domain
        String username = trimmedEmail.substring(0, firstAtIndex);
        String domain = trimmedEmail.substring(firstAtIndex + 1);

        // Validate non-empty username
        if (username.isEmpty()) {
            throw new InvalidEmailException(
                    "Email address must have a non-empty username.");
        }

        // Validate non-empty domain
        if (domain.isEmpty()) {
            throw new InvalidEmailException(
                    "Email address must have a non-empty domain.");
        }

        // Return the normalized (trimmed) email
        return trimmedEmail;
    }
}
