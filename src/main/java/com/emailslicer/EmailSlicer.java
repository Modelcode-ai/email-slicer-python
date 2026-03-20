package com.emailslicer;

import java.util.Optional;

/**
 * Utility class that parses an email address into its username and domain parts.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Slices an email address into username and domain parts.
     *
     * <p>Validation rules:
     * <ul>
     *   <li>Input must not be null or blank</li>
     *   <li>Input must contain exactly one {@code @} character</li>
     *   <li>Neither the username nor domain part may be empty</li>
     * </ul>
     *
     * @param email the email address to parse
     * @return an {@link Optional} containing the parsed {@link EmailParts},
     *         or {@link Optional#empty()} if the input is invalid
     */
    public static Optional<EmailParts> slice(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        // Ensure there is exactly one '@'
        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty() || domain.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new EmailParts(username, domain));
    }
}
