package com.example.emailslicer;

/**
 * Email validation and parsing logic.
 * Provides methods to validate email addresses and extract username and domain parts.
 */
public final class EmailParser {

    private EmailParser() {
        // Utility class; prevent instantiation
    }

    /**
     * Validates whether the given email address is valid.
     * Returns false for any invalid input and never throws exceptions.
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    public static boolean isValidEmail(final String email) {
        if (email == null) {
            return false;
        }

        final String trimmed = email.strip();

        if (trimmed.isEmpty()) {
            return false;
        }

        final int atIndex = trimmed.indexOf('@');

        // Must have exactly one @ symbol
        if (atIndex == -1) {
            return false;
        }

        // Check for multiple @ symbols
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            return false;
        }

        // @ cannot be at the start or end
        if (atIndex == 0 || atIndex == trimmed.length() - 1) {
            return false;
        }

        return true;
    }

    /**
     * Splits an email address into username and domain parts.
     * Throws IllegalArgumentException if the email is invalid.
     *
     * @param email the email address to split
     * @return an array containing [username, domain]
     * @throws IllegalArgumentException if the email is invalid
     */
    public static String[] splitEmail(final String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email address");
        }

        final String trimmed = email.strip();
        final int atIndex = trimmed.indexOf('@');

        final String username = trimmed.substring(0, atIndex);
        final String domain = trimmed.substring(atIndex + 1);

        return new String[]{username, domain};
    }

    /**
     * Extracts the username part from an email address.
     * Throws IllegalArgumentException if the email is invalid.
     *
     * @param email the email address
     * @return the username part (before the @)
     * @throws IllegalArgumentException if the email is invalid
     */
    public static String extractUsername(final String email) {
        return splitEmail(email)[0];
    }

    /**
     * Extracts the domain part from an email address.
     * Throws IllegalArgumentException if the email is invalid.
     *
     * @param email the email address
     * @return the domain part (after the @)
     * @throws IllegalArgumentException if the email is invalid
     */
    public static String extractDomain(final String email) {
        return splitEmail(email)[1];
    }
}
