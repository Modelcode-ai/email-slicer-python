package com.modelcode.emailslicer.core;

/**
 * Core email parsing and validation logic.
 * This class provides functionality to parse email addresses into their
 * username and domain components with proper validation.
 *
 * <p>The parser applies the following validation rules:</p>
 * <ul>
 *   <li>Input must not be empty after trimming whitespace</li>
 *   <li>Input must contain exactly one '@' character</li>
 *   <li>Both username (before '@') and domain (after '@') must be
 *       non-empty</li>
 * </ul>
 *
 * <p>This class contains no console I/O and can be reused as a library
 * component.</p>
 */
public class EmailSlicer {

    /**
     * Parses an email address into its username and domain components.
     *
     * <p>The method normalizes the input by trimming whitespace, validates
     * the format, and splits the email at the '@' character.</p>
     *
     * @param email the email address to parse (may contain leading/trailing
     *              whitespace)
     * @return an EmailComponents object containing the username and domain
     * @throws InvalidEmailException if the email fails any validation rule
     */
    public EmailComponents slice(final String email)
            throws InvalidEmailException {
        // Normalize input by trimming whitespace (equivalent to Python's
        // strip())
        final String trimmedEmail = email.trim();

        // Validation 1: Check non-empty input
        if (trimmedEmail.isEmpty()) {
            throw new InvalidEmailException(
                "Invalid email address. Please make sure it contains a "
                + "username, a single '@', and a domain.");
        }

        // Validation 2: Check exactly one '@'
        final int firstAt = trimmedEmail.indexOf('@');
        final int lastAt = trimmedEmail.lastIndexOf('@');

        if (firstAt == -1 || firstAt != lastAt) {
            throw new InvalidEmailException(
                "Invalid email address. Please make sure it contains a "
                + "username, a single '@', and a domain.");
        }

        // Parse username and domain by splitting at '@'
        final String username = trimmedEmail.substring(0, firstAt);
        final String domain = trimmedEmail.substring(firstAt + 1);

        // Validation 3: Check non-empty parts
        if (username.isEmpty() || domain.isEmpty()) {
            throw new InvalidEmailException(
                "Invalid email address. Please make sure it contains a "
                + "username, a single '@', and a domain.");
        }

        return new EmailComponents(username, domain);
    }
}
