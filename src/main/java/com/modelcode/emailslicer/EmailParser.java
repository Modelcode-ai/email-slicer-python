package com.modelcode.emailslicer;

/**
 * Service class for parsing and validating email addresses.
 *
 * <p>This parser performs basic structural validation and extracts
 * the username (local part) and domain from an email address. It
 * uses simple string operations to check for:
 * <ul>
 *   <li>Exactly one '@' symbol</li>
 *   <li>Non-empty username before the '@'</li>
 *   <li>Non-empty domain after the '@'</li>
 * </ul>
 *
 * <p>The parser does not perform full RFC 5322 email validation.
 * It's designed for educational purposes and basic use cases.
 *
 * <p>Example usage:
 * <pre>{@code
 * EmailParser parser = new EmailParser();
 * EmailParts parts = parser.parse("user@example.com");
 * System.out.println(parts.username()); // "user"
 * System.out.println(parts.domain());   // "example.com"
 * }</pre>
 */
public class EmailParser {

    /**
     * Parses an email address into its username and domain
     * components.
     *
     * <p>This method validates the email structure and extracts
     * the parts using string slicing:
     * <ul>
     *   <li>Trims leading and trailing whitespace from the
     *       input</li>
     *   <li>Validates that exactly one '@' symbol exists</li>
     *   <li>Ensures both username and domain are non-empty</li>
     *   <li>Uses {@code String.indexOf} to locate the '@'
     *       separator</li>
     *   <li>Uses {@code String.substring} to extract the
     *       username and domain</li>
     * </ul>
     *
     * @param email the email address to parse (may contain
     *              leading/trailing whitespace)
     * @return an {@link EmailParts} object containing the
     *         extracted username and domain
     * @throws InvalidEmailException if the email is null, empty,
     *         or doesn't meet structural requirements
     */
    public EmailParts parse(final String email) {
        // Handle null input
        if (email == null) {
            throw new InvalidEmailException("Email cannot be null");
        }

        // Trim whitespace and check for empty string
        String trimmedEmail = email.trim();
        if (trimmedEmail.isEmpty()) {
            throw new InvalidEmailException("Email cannot be empty");
        }

        // Find the position of '@' symbol
        int atIndex = trimmedEmail.indexOf('@');
        int lastAtIndex = trimmedEmail.lastIndexOf('@');

        // Check for no '@' symbol
        if (atIndex == -1) {
            throw new InvalidEmailException(
                "Email must contain exactly one '@' character");
        }

        // Check for multiple '@' symbols
        if (atIndex != lastAtIndex) {
            throw new InvalidEmailException(
                "Email must contain exactly one '@' character");
        }

        // Check for '@' at the beginning (empty username)
        if (atIndex == 0) {
            throw new InvalidEmailException(
                "Email must have a non-empty username before '@'");
        }

        // Check for '@' at the end (empty domain)
        if (atIndex == trimmedEmail.length() - 1) {
            throw new InvalidEmailException(
                "Email must have a non-empty domain after '@'");
        }

        // Extract username and domain using string slicing
        String username = trimmedEmail.substring(0, atIndex);
        String domain = trimmedEmail.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
