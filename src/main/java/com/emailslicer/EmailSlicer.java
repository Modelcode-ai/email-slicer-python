package com.emailslicer;

import java.util.Optional;

/**
 * Core email slicing logic — parses an email address into its username and domain parts.
 */
public final class EmailSlicer {

    /** Error message displayed when the email address is invalid. */
    public static final String INVALID_EMAIL_MESSAGE = "Please enter a valid Email Id.";

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Parses the given email string into its username and domain components.
     *
     * <p>The input is trimmed before validation. An email is considered valid when it
     * contains exactly one {@code @} symbol with non-empty text on both sides and no
     * internal whitespace.</p>
     *
     * @param email the raw email string (may be {@code null})
     * @return an {@link Optional} containing the parsed {@link EmailParts},
     *         or {@link Optional#empty()} if the input is invalid
     */
    public static Optional<EmailParts> slice(String email) {
        if (email == null) {
            return Optional.empty();
        }

        String trimmed = email.trim();

        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        // Reject if there is internal whitespace after trimming
        if (trimmed.contains(" ") || trimmed.contains("\t")) {
            return Optional.empty();
        }

        String[] parts = trimmed.split("@", -1);

        // Must have exactly two parts (exactly one @)
        if (parts.length != 2) {
            return Optional.empty();
        }

        String username = parts[0];
        String domain = parts[1];

        // Reject empty username or domain
        if (username.isEmpty() || domain.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new EmailParts(username, domain));
    }
}
