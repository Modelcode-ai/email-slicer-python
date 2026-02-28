package emailslicer;

/**
 * Core parsing and validation logic for email addresses.
 * <p>
 * Splits an email string into its username (local part) and domain
 * using the first occurrence of {@code @}, mirroring the behavior of
 * the original Python {@code emailSlicer.py} script.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not meant to be instantiated
    }

    /**
     * Parses an email address into its username and domain parts.
     * <p>
     * The input is trimmed of leading and trailing whitespace before parsing.
     * The first {@code @} character is used as the separator, so an address
     * like {@code user@sub@example.com} yields username {@code user} and
     * domain {@code sub@example.com}.
     *
     * @param input the raw email address string (may include surrounding whitespace)
     * @return an {@link EmailParts} record containing the username and domain
     * @throws InvalidEmailException if the input is null, empty after trimming,
     *                               or does not contain a valid {@code @} separator
     */
    public static EmailParts parse(String input) {
        if (input == null) {
            throw new InvalidEmailException("Email must not be null");
        }

        String trimmed = input.strip();

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Email must not be empty");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new InvalidEmailException("Email must contain an '@' character");
        }

        if (atIndex == 0) {
            throw new InvalidEmailException("Email must have a username before '@'");
        }

        if (atIndex == trimmed.length() - 1) {
            throw new InvalidEmailException("Email must have a domain after '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
