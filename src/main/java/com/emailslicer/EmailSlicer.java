package com.emailslicer;

/**
 * Core parsing and validation engine for email addresses.
 * <p>
 * Accepts a raw input string, trims whitespace, locates the first {@code @}
 * character, validates the split, and returns an {@link EmailSliceResult}.
 * Throws {@link IllegalArgumentException} for invalid inputs.
 * <p>
 * Parsing rules mirror the original Python implementation:
 * <ul>
 *   <li>Whitespace is trimmed (equivalent to Python {@code strip()}).</li>
 *   <li>The first {@code @} is used as the split point (equivalent to Python {@code index("@")}).</li>
 *   <li>Multiple {@code @} characters result in everything after the first being part of the domain.</li>
 * </ul>
 */
public class EmailSlicer {

    /**
     * Slices an email address into its username and domain components.
     *
     * @param rawInput the raw email address string (may contain leading/trailing whitespace)
     * @return an {@link EmailSliceResult} containing the username and domain
     * @throws IllegalArgumentException if the input is null, empty, or not a valid email format
     */
    public EmailSliceResult slice(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String email = rawInput.trim();

        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty");
        }

        int atIndex = email.indexOf('@');

        if (atIndex < 0) {
            throw new IllegalArgumentException("Email must contain an '@' character");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }

        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain must not be empty");
        }

        return new EmailSliceResult(username, domain);
    }
}
