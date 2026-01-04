package com.emailslicer;

/**
 * Core email parsing and validation logic.
 * This class is responsible for validating email addresses and extracting
 * username and domain components.
 *
 * <p>Validation rules:</p>
 * <ul>
 *   <li>Input must contain exactly one @ character</li>
 *   <li>Username (part before @) must be non-empty</li>
 *   <li>Domain (part after @) must be non-empty</li>
 * </ul>
 *
 * <p>This class is stateless and can be reused by other Java code.</p>
 */
public final class EmailParser {

    private EmailParser() {
        // Prevent instantiation - use static method
    }

    /**
     * Parses a raw email input string and extracts username and domain.
     *
     * <p>The input is trimmed of leading/trailing whitespace first.</p>
     *
     * @param rawInput the raw email address input (may include whitespace)
     * @return a ParsedEmail with username and domain, or null if invalid
     */
    public static ParsedEmail parse(final String rawInput) {
        if (rawInput == null) {
            return null;
        }

        String email = rawInput.trim();

        if (email.isEmpty()) {
            return null;
        }

        // Check for exactly one @ character
        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            // No @ character found
            return null;
        }

        // Check for multiple @ characters
        if (email.indexOf('@', atIndex + 1) != -1) {
            return null;
        }

        // Check that @ is not at the start (empty username)
        if (atIndex == 0) {
            return null;
        }

        // Check that @ is not at the end (empty domain)
        if (atIndex == email.length() - 1) {
            return null;
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }
}
