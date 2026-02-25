package emailslicer;

/**
 * Utility class that parses an email address into its username and domain parts.
 *
 * <p>Parsing mirrors the behaviour of the original Python Email Slicer script:
 * the input is trimmed, validated for the presence of an {@code @} character,
 * and split at the <em>first</em> {@code @} into username and domain.
 *
 * <p>Inputs like {@code "@domain.com"} (empty username) and {@code "user@"}
 * (empty domain) are treated as <strong>valid</strong> to preserve parity with
 * the original Python implementation.
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawEmail the raw email address (may contain leading/trailing whitespace)
     * @return an {@link EmailParts} instance with the username and domain
     * @throws InvalidEmailException if the input is null, empty, whitespace-only,
     *                                or does not contain an {@code @} character
     */
    public static EmailParts parse(String rawEmail) {
        if (rawEmail == null) {
            throw new InvalidEmailException("Email must not be null");
        }

        String trimmed = rawEmail.trim();

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Email must not be empty");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new InvalidEmailException("Email must contain an '@' character");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Immutable holder for the username and domain parts of an email address.
     */
    public static class EmailParts {

        private final String username;
        private final String domain;

        public EmailParts(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }
}
