package com.emailslicer;

/**
 * Core service class for parsing and validating email addresses.
 *
 * <p>This class provides the main business logic for extracting the
 * username and domain components from an email address string using
 * string slicing operations (similar to Python's string slicing with
 * {@code email.index("@")} and substring extraction).</p>
 *
 * <p><b>Validation Rules:</b></p>
 * <ul>
 *   <li>Email must not be null</li>
 *   <li>Email must not be empty or contain only whitespace</li>
 *   <li>Email must contain exactly one '@' symbol</li>
 *   <li>'@' symbol must not be at the beginning (username cannot be empty)</li>
 *   <li>'@' symbol must not be at the end (domain cannot be empty)</li>
 * </ul>
 *
 * <p><b>Parsing Approach:</b></p>
 * <p>The implementation uses {@code indexOf("@")} and {@code substring()}
 * operations to mirror Python's string slicing approach, making it
 * educational for developers transitioning from Python to Java.</p>
 *
 * <p><b>Example usage:</b></p>
 * <pre>{@code
 * EmailSlicer slicer = new EmailSlicer();
 * try {
 *     EmailResult result = slicer.parse("user@example.com");
 *     System.out.println("Username: " + result.username());
 *     System.out.println("Domain: " + result.domain());
 * } catch (InvalidEmailException e) {
 *     System.err.println("Invalid email: " + e.getMessage());
 * }
 * }</pre>
 *
 * @since 1.0.0
 */
public class EmailSlicer {

    /**
     * Parses an email address into its username and domain components.
     *
     * <p>This method validates the input email and extracts the username
     * (the part before '@') and domain (the part after '@') using string
     * slicing operations.</p>
     *
     * <p>The input is automatically trimmed of leading and trailing
     * whitespace before validation.</p>
     *
     * @param emailInput the email address to parse
     * @return an {@link EmailResult} containing the username and domain
     * @throws InvalidEmailException if the email is null, empty, or does
     *         not meet the validation criteria
     */
    public EmailResult parse(final String emailInput)
            throws InvalidEmailException {
        // Validation step 1: Check for null input
        if (emailInput == null) {
            throw new InvalidEmailException(
                    "Email address must not be null.");
        }

        // Validation step 2: Trim whitespace and check for empty input
        final String email = emailInput.trim();
        if (email.isEmpty()) {
            throw new InvalidEmailException(
                    "Email address must not be empty.");
        }

        // Validation step 3: Count '@' symbols
        final int atCount = countOccurrences(email, '@');
        if (atCount == 0) {
            throw new InvalidEmailException(
                    "Email address must contain a single '@' symbol.");
        }
        if (atCount > 1) {
            throw new InvalidEmailException(
                    "Email address must contain exactly one '@' symbol.");
        }

        // Validation step 4: Find the position of '@'
        final int atIndex = email.indexOf('@');

        // Validation step 5: Check '@' is not at the start (empty username)
        if (atIndex == 0) {
            throw new InvalidEmailException(
                    "Email username must not be empty.");
        }

        // Validation step 6: Check '@' is not at the end (empty domain)
        if (atIndex == email.length() - 1) {
            throw new InvalidEmailException(
                    "Email domain must not be empty.");
        }

        // Parsing step 1: Extract username using string slicing
        // This mirrors Python's email[:email.index("@")]
        final String username = email.substring(0, atIndex);

        // Parsing step 2: Extract domain using string slicing
        // This mirrors Python's email[email.index("@") + 1:]
        final String domain = email.substring(atIndex + 1);

        // Return the parsed result
        return new EmailResult(username, domain);
    }

    /**
     * Counts the number of occurrences of a specific character in a string.
     *
     * <p>This helper method is used to validate that the email contains
     * exactly one '@' symbol.</p>
     *
     * @param text the string to search in
     * @param character the character to count
     * @return the number of times the character appears in the text
     */
    private int countOccurrences(final String text, final char character) {
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }
}
