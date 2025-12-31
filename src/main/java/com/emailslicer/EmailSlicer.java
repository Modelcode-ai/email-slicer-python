package com.emailslicer;

/**
 * Parses email addresses into username and domain components.
 *
 * <p>This class provides email parsing functionality that extracts the
 * username (local part) and domain from an email address string. The
 * parsing logic mirrors the behavior of the original Python implementation
 * using string slicing, while adding comprehensive validation.
 *
 * <p><strong>Validation Behavior:</strong> This implementation performs
 * basic structural validation including:
 * <ul>
 *   <li>Null and empty string checks</li>
 *   <li>Exactly one @ symbol requirement</li>
 *   <li>Non-empty username and domain parts</li>
 *   <li>Optional simple domain rules (at least one dot, no whitespace)</li>
 * </ul>
 *
 * <p><strong>Note:</strong> This is not fully RFC-compliant email
 * validation. It performs simple structural checks suitable for basic
 * email parsing scenarios. For production email validation, consider
 * using a dedicated library.
 */
public class EmailSlicer {

    /**
     * Controls whether simple domain validation rules are enforced.
     * When enabled, domains must contain at least one dot and no whitespace.
     */
    private static final boolean ENABLE_SIMPLE_DOMAIN_VALIDATION = true;

    /**
     * Parses a raw email string into an EmailAddress object.
     *
     * <p>The method performs the following operations:
     * <ol>
     *   <li>Validates that input is not null</li>
     *   <li>Strips leading and trailing whitespace from the input</li>
     *   <li>Validates that the trimmed string is not empty</li>
     *   <li>Validates that exactly one @ symbol exists</li>
     *   <li>Splits the string into username (before @) and domain
     *   (after @)</li>
     *   <li>Validates that both username and domain are non-empty</li>
     *   <li>Optionally validates simple domain rules (if enabled)</li>
     *   <li>Returns an EmailAddress instance with the extracted
     *   components</li>
     * </ol>
     *
     * <p><strong>Validation Rules:</strong>
     * <ul>
     *   <li>Input must not be null</li>
     *   <li>Input must not be empty (after trimming whitespace)</li>
     *   <li>Must contain exactly one @ character</li>
     *   <li>Username (part before @) must be non-empty</li>
     *   <li>Domain (part after @) must be non-empty</li>
     *   <li>If simple domain validation is enabled, domain must contain
     *   at least one dot and no whitespace</li>
     * </ul>
     *
     * <p><strong>Compatibility Note:</strong> For all inputs that the
     * original Python script treats as valid (contains an "@" and produces
     * non-empty username and domain when sliced), this Java version produces
     * the same username and domain substrings. However, the Java version
     * may reject some inputs that Python accepted if they violate the
     * validation rules above.
     *
     * @param rawEmail the raw email string to parse
     * @return an EmailAddress object containing the username and domain
     * @throws EmailValidationException if the email fails any validation
     *         rule
     */
    public EmailAddress parse(final String rawEmail) {
        // Validation step 1: Check for null input
        if (rawEmail == null) {
            throw new EmailValidationException(
                "Email address must not be null.");
        }

        // Validation step 2: Strip leading and trailing whitespace
        String email = rawEmail.strip();

        // Validation step 3: Check for empty string after stripping
        if (email.isEmpty()) {
            throw new EmailValidationException(
                "Email address must not be empty.");
        }

        // Validation step 4: Count @ symbols and ensure exactly one exists
        long atCount = email.chars().filter(ch -> ch == '@').count();
        if (atCount != 1) {
            throw new EmailValidationException(
                "Email address must contain exactly one '@' character.");
        }

        // Find the position of the @ symbol
        int atIndex = email.indexOf("@");

        // Extract username (from start to @ symbol)
        String username = email.substring(0, atIndex);

        // Extract domain (from after @ symbol to end)
        String domain = email.substring(atIndex + 1);

        // Validation step 5: Check for non-empty username
        if (username.isEmpty()) {
            throw new EmailValidationException(
                "Email address must contain a non-empty username before '@'.");
        }

        // Validation step 6: Check for non-empty domain
        if (domain.isEmpty()) {
            throw new EmailValidationException(
                "Email address must contain a non-empty domain after '@'.");
        }

        // Validation step 7: Optional simple domain validation
        if (ENABLE_SIMPLE_DOMAIN_VALIDATION) {
            if (!domain.contains(".") || domain.contains(" ")) {
                throw new EmailValidationException(
                    "Email domain must contain at least one '.' "
                    + "and no whitespace.");
            }
        }

        // Return the parsed email address
        return new EmailAddress(username, domain);
    }
}
