package com.emailslicer;

import java.util.Optional;

/**
 * Utility class that parses an email address into its username and domain components.
 * <p>
 * This mirrors the Python original's behavior: the input is trimmed, checked for the
 * presence of {@code @}, and split into username (before {@code @}) and domain (after {@code @}).
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Slices an email address into its username and domain parts.
     *
     * @param email the raw email string (may include leading/trailing whitespace)
     * @return an {@link Optional} containing the parsed {@link EmailResult},
     *         or {@link Optional#empty()} if the input is null or does not contain {@code @}
     */
    public static Optional<EmailResult> slice(String email) {
        if (email == null) {
            return Optional.empty();
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailResult(username, domain));
    }
}
