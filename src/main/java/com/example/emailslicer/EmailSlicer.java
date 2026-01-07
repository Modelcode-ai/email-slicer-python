package com.example.emailslicer;

/**
 * Core email parsing and validation logic.
 *
 * <p>This class provides methods to validate and parse email addresses
 * using minimal validation (checking only for '@' presence). The parsing
 * behavior matches Python's string slicing operations.
 */
public final class EmailSlicer {

    /**
     * Validates whether a string represents a valid email address.
     *
     * <p>An email is considered valid if, after trimming whitespace, it is
     * non-empty and contains at least one '@' character. This matches the
     * Python version's validation logic.
     *
     * @param email the email string to validate (may be null)
     * @return true if the email is valid, false otherwise
     */
    public boolean isValidEmail(final String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        return !trimmed.isEmpty() && trimmed.indexOf('@') != -1;
    }

    /**
     * Parses an email address into username and domain components.
     *
     * <p>The email is trimmed, then split at the first '@' character.
     * The username is everything before the first '@', and the domain
     * is everything after it. This exactly replicates Python's string
     * slicing behavior.
     *
     * @param email the email string to parse
     *              (must not be null and must contain '@')
     * @return an EmailResult containing the username and domain
     * @throws IllegalArgumentException if email is null or does not contain '@'
     */
    public EmailResult parseEmail(final String email) {
        if (email == null) {
            throw new IllegalArgumentException("email must not be null");
        }
        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("email must contain '@'");
        }
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);
        return new EmailResult(username, domain);
    }
}
