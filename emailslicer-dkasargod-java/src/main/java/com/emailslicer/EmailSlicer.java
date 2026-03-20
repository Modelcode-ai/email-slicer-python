package com.emailslicer;

/**
 * Parses an email address string into its username and domain components.
 *
 * <p>This class contains the core parsing logic, separated from I/O concerns
 * to allow direct unit testing without mocking {@code System.in}/{@code System.out}.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Parses the given email string into an {@link EmailParts} record.
     *
     * <p>The input is trimmed before validation. Validation requires:
     * <ul>
     *   <li>Input is not null or blank</li>
     *   <li>Input contains exactly one {@code @} character</li>
     *   <li>Both the username (before {@code @}) and domain (after {@code @}) are non-empty</li>
     * </ul>
     *
     * @param email the raw email address string
     * @return an {@link EmailParts} instance containing the username and domain
     * @throws IllegalArgumentException if the input fails validation
     */
    public static EmailParts parse(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL_MESSAGE);
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL_MESSAGE);
        }

        // Reject multiple '@' characters
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL_MESSAGE);
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty() || domain.isEmpty()) {
            throw new IllegalArgumentException(Messages.INVALID_EMAIL_MESSAGE);
        }

        return new EmailParts(username, domain);
    }
}
