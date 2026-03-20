package com.emailslicer;

/**
 * Parses an email address string into its username and domain components.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // utility class — not instantiable
    }

    /**
     * Parses the given email string into an {@link EmailParts} record.
     *
     * <p>The input is trimmed of leading/trailing whitespace before parsing.
     * Validation rules:
     * <ul>
     *   <li>Must not be {@code null} or blank</li>
     *   <li>Must contain exactly one {@code @} character</li>
     *   <li>Both the username (before {@code @}) and domain (after {@code @}) must be non-empty</li>
     * </ul>
     *
     * @param email the raw email address string
     * @return an {@link EmailParts} containing the username and domain
     * @throws IllegalArgumentException if the input fails any validation rule
     */
    public static EmailParts parse(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        // Check for multiple @ characters
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty() || domain.isEmpty()) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL);
        }

        return new EmailParts(username, domain);
    }
}
