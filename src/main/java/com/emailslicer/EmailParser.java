package com.emailslicer;

/**
 * Utility class for parsing and validating email addresses.
 * <p>
 * This parser implements simple validation logic that mirrors the original Python script:
 * - Trims leading and trailing whitespace (equivalent to Python's strip())
 * - Validates that the email contains at least one '@' symbol
 * - Splits on the first '@' symbol, treating everything before as username and after as domain
 * - Returns null for invalid input (no exceptions thrown)
 * <p>
 * Edge cases:
 * - "@domain.com" is valid (empty username)
 * - "user@" is valid (empty domain)
 * - "user@sub@domain.com" splits on first '@' only (username="user", domain="sub@domain.com")
 */
public final class EmailParser {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EmailParser() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Parses a raw email string into its username and domain components.
     * <p>
     * The parsing logic:
     * 1. Returns null if input is null
     * 2. Trims leading and trailing whitespace
     * 3. Returns null if the trimmed string is empty
     * 4. Returns null if the email does not contain an '@' symbol
     * 5. Splits on the first '@' symbol to extract username and domain
     *
     * @param rawEmail the raw email string to parse (may contain whitespace, may be null)
     * @return an EmailParts object containing the username and domain, or null if the input is invalid
     */
    public static EmailParts parse(String rawEmail) {
        // Handle null input
        if (rawEmail == null) {
            return null;
        }

        // Trim whitespace (equivalent to Python's strip())
        String email = rawEmail.trim();

        // Validate non-empty
        if (email.isEmpty()) {
            return null;
        }

        // Find the first '@' symbol
        int atIndex = email.indexOf('@');

        // Validate that '@' exists
        if (atIndex < 0) {
            return null;
        }

        // Extract username (everything before first '@')
        String username = email.substring(0, atIndex);

        // Extract domain (everything after first '@')
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
