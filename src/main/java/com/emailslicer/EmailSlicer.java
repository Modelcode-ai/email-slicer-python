package com.emailslicer;

/**
 * Core email parsing and validation logic.
 * Splits an email address into username and domain components
 * using the first {@code @} character as the separator.
 */
public final class EmailSlicer {

    public record EmailParts(String username, String domain) {}

    private static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Parses the given email string into username and domain parts.
     * The split is performed at the first {@code @} character.
     *
     * @param email the email address to parse (must not be null)
     * @return an {@link EmailParts} record containing the username and domain
     * @throws IllegalArgumentException if the email is null, empty, or does not contain {@code @}
     */
    public static EmailParts parse(String email) {
        if (email == null || email.isEmpty() || email.indexOf('@') == -1) {
            throw new IllegalArgumentException(INVALID_EMAIL_MESSAGE);
        }

        int atIndex = email.indexOf('@');
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
