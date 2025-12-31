package com.emailslicer;

/**
 * Parses email addresses into username and domain components.
 *
 * <p>This class provides basic email parsing functionality that
 * extracts the username (local part) and domain from an email address
 * string. The parsing logic mirrors the behavior of the original
 * Python implementation using string slicing.
 *
 * <p><strong>Note:</strong> This is a basic implementation with
 * minimal validation. It performs simple structural checks but is not
 * fully RFC-compliant. Comprehensive validation will be added in
 * subsequent iterations.
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into an EmailAddress object.
     *
     * <p>The method performs the following operations:
     * <ol>
     *   <li>Strips leading and trailing whitespace from the input</li>
     *   <li>Locates the @ symbol</li>
     *   <li>Splits the string into username (before @) and domain
     *   (after @)</li>
     *   <li>Returns an EmailAddress instance with the extracted
     *   components</li>
     * </ol>
     *
     * <p><strong>Current Implementation Note:</strong> This basic
     * version performs minimal validation. It only checks for the
     * presence of the @ symbol. More comprehensive validation (null
     * checks, empty strings, multiple @ symbols, etc.) will be
     * implemented in a subsequent task.
     *
     * @param rawEmail the raw email string to parse
     * @return an EmailAddress object containing the username and domain
     * @throws IllegalArgumentException if the email does not contain
     * an @ symbol
     */
    public EmailAddress parse(final String rawEmail) {
        // Strip leading and trailing whitespace
        String email = rawEmail.strip();

        // Find the position of the @ symbol
        int atIndex = email.indexOf("@");

        // Basic validation: check if @ symbol exists
        if (atIndex == -1) {
            throw new IllegalArgumentException(
                "Email address must contain an '@' character.");
        }

        // Extract username (from start to @ symbol)
        String username = email.substring(0, atIndex);

        // Extract domain (from after @ symbol to end)
        String domain = email.substring(atIndex + 1);

        // Return the parsed email address
        return new EmailAddress(username, domain);
    }
}
