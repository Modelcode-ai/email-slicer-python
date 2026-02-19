package com.emailslicer;

/**
 * Utility class that parses a raw email string into its username and domain
 * components using first-{@code @} splitting semantics.
 *
 * <p>This mirrors the behavior of the original Python implementation
 * ({@code str.index("@")} + slicing), with additional validation for
 * null, empty, and structurally invalid inputs.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // utility class; prevent instantiation
    }

    /**
     * Parses a raw email string into an {@link EmailComponents} record.
     *
     * <p>The input is trimmed of leading and trailing whitespace before
     * validation. The first {@code @} character determines the split point:
     * everything before it becomes the username, everything after becomes
     * the domain.</p>
     *
     * @param rawEmail the raw email string to parse
     * @return an {@link EmailComponents} containing the username and domain
     * @throws InvalidEmailException if the input is null, empty after trimming,
     *         missing an {@code @} symbol, or has an empty username or domain
     */
    public static EmailComponents parse(String rawEmail) {
        if (rawEmail == null) {
            throw new InvalidEmailException("Email address must not be null");
        }

        String trimmed = rawEmail.trim();

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Email address must not be empty");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new InvalidEmailException("Email address must contain an '@' symbol");
        }

        if (atIndex == 0) {
            throw new InvalidEmailException("Email address must have a non-empty username before '@'");
        }

        if (atIndex == trimmed.length() - 1) {
            throw new InvalidEmailException("Email address must have a non-empty domain after '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailComponents(username, domain);
    }
}
