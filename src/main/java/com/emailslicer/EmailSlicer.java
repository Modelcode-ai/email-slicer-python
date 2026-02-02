package com.emailslicer;

import java.util.regex.Pattern;

/**
 * Core email parsing and validation logic for the Email Slicer tool.
 * This utility class provides methods to validate email addresses and extract
 * the username and domain components.
 */
public final class EmailSlicer {

    /**
     * Regex pattern for basic email validation.
     * Requires:
     * - Non-empty local part (before @)
     * - Exactly one @ symbol
     * - Non-empty domain part (after @) with at least one dot
     */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+\\.[^@]+$");

    /**
     * Error message for invalid email addresses.
     */
    private static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";

    /**
     * Immutable value object representing the result of email parsing.
     * Contains the username (local part) and domain components of an email address.
     */
    public static final class Result {
        private final String username;
        private final String domain;

        /**
         * Constructs a new Result with the specified username and domain.
         *
         * @param username the local part of the email (before @)
         * @param domain the domain part of the email (after @)
         */
        public Result(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        /**
         * Returns the username (local part) of the email address.
         *
         * @return the username
         */
        public String getUsername() {
            return username;
        }

        /**
         * Returns the domain part of the email address.
         *
         * @return the domain
         */
        public String getDomain() {
            return domain;
        }
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EmailSlicer() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Parses an email address into its username and domain components.
     * The input is normalized (whitespace trimmed) and validated before parsing.
     *
     * @param email the email address to parse
     * @return a Result object containing the username and domain
     * @throws IllegalArgumentException if the email is invalid
     */
    public static Result slice(String email) {
        String normalized = normalize(email);
        if (!isValidEmail(normalized)) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }
        int atIndex = normalized.indexOf('@');
        String username = normalized.substring(0, atIndex);
        String domain = normalized.substring(atIndex + 1);
        return new Result(username, domain);
    }

    /**
     * Normalizes an email address by trimming leading and trailing whitespace.
     * Handles null input by returning an empty string.
     *
     * @param email the email address to normalize (may be null)
     * @return the normalized email address (never null)
     */
    static String normalize(String email) {
        return email == null ? "" : email.strip();
    }

    /**
     * Validates an email address using a regex pattern.
     * Checks for:
     * - Non-empty local part
     * - Exactly one @ symbol
     * - Non-empty domain with at least one dot
     *
     * @param email the email address to validate
     * @return true if the email is valid, false otherwise
     */
    static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
    }
}
