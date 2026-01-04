package com.emailslicer;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Parser and validator for email addresses.
 * Provides static methods to validate email structure and extract
 * username and domain components.
 * Uses stricter validation than the original Python implementation,
 * requiring proper email structure with non-empty username, non-empty
 * domain with at least one dot, no whitespace, and a single @ symbol.
 */
public final class EmailParser {

    /**
     * Regular expression pattern for email validation.
     * Ensures:
     * - Non-empty username before @
     * - Non-empty domain after @
     * - Domain contains at least one dot
     * - No whitespace anywhere in the email
     * - Single @ symbol
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only static methods.
     */
    private EmailParser() {
        throw new AssertionError("EmailParser cannot be instantiated");
    }

    /**
     * Parses and validates an email address.
     *
     * @param rawEmail the email address to parse (may be null or
     *                 contain whitespace)
     * @return ParsedEmail containing the username and domain components
     * @throws ValidationException if the email is null, empty, or
     *                             doesn't meet validation criteria
     */
    public static ParsedEmail parse(final String rawEmail) {
        // Check for null input
        Objects.requireNonNull(rawEmail, "Email must not be null");

        // Trim whitespace and check for empty
        final String email = rawEmail.trim();
        if (email.isEmpty()) {
            throw new ValidationException("Email must not be empty");
        }

        // Validate email format using regex
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new ValidationException("Email format is invalid");
        }

        // Parse username and domain
        final int atIndex = email.indexOf('@');
        final String username = email.substring(0, atIndex);
        final String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Represents a parsed email address with username and domain
     * components.
     * This is an immutable data structure with automatic equals,
     * hashCode, and toString implementations.
     *
     * @param username the username part before the @ symbol
     * @param domain the domain part after the @ symbol
     */
    public record ParsedEmail(String username, String domain) {
    }
}
