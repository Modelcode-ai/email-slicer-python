package com.emailslicer;

/**
 * Parses an email address string into its username and domain components.
 * <p>
 * The split is performed on the <em>first</em> {@code @} character, mirroring
 * the behavior of Python's {@code str.index("@")}.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Splits the given email address at the first {@code @} character and returns
     * an {@link EmailParts} record containing the username and domain.
     * <p>
     * Leading and trailing whitespace is stripped before parsing, consistent with
     * the original Python implementation's use of {@code strip()}.
     *
     * @param email the email address to slice
     * @return an {@link EmailParts} record with the parsed username and domain
     * @throws IllegalArgumentException if {@code email} is {@code null}, empty,
     *                                  or does not contain an {@code @} character
     */
    public static EmailParts slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email address must not be null.");
        }

        String trimmed = email.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email address must not be empty.");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Email address must contain an '@' character.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
