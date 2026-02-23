package com.emailslicer;

/**
 * Parses an email address into its username and domain components.
 * <p>
 * This class encapsulates the email parsing and validation logic,
 * mirroring the behavior of the original Python {@code emailSlicer.py} script.
 */
public class EmailSlicer {

    /**
     * Parses the given email string into its username and domain parts.
     * <p>
     * The input is trimmed of leading and trailing whitespace before processing.
     * The split is performed on the first {@code @} character, mirroring Python's
     * {@code str.index("@")} behavior.
     *
     * @param email the email address to parse
     * @return an {@link EmailParts} record containing the username and domain
     * @throws IllegalArgumentException if the input is {@code null}, empty after
     *         trimming, does not contain {@code @}, or results in an empty
     *         username or domain
     */
    public EmailParts parse(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null.");
        }

        String trimmed = email.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email must not be empty.");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an '@' character.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty.");
        }

        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain must not be empty.");
        }

        return new EmailParts(username, domain);
    }
}
