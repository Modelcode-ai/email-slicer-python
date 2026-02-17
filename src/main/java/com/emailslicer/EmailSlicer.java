package com.emailslicer;

import java.util.Optional;

/**
 * Core email parsing and validation logic.
 * Mirrors the Python script's semantics: splits on the first {@code @} character.
 */
public final class EmailSlicer {

    private EmailSlicer() {
    }

    /**
     * Parses an email string into username and domain parts.
     *
     * <p>The email is split on the first {@code @} character. If no {@code @} is found,
     * or if the username (before {@code @}) or domain (after {@code @}) would be empty,
     * an empty optional is returned.
     *
     * @param email the email string to parse (should already be trimmed)
     * @return an {@link Optional} containing {@link EmailParts} if valid, or empty if invalid
     */
    public static Optional<EmailParts> parse(String email) {
        if (email == null) {
            return Optional.empty();
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        if (username.isEmpty() || domain.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new EmailParts(username, domain));
    }
}
