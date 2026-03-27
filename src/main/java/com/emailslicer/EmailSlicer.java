package com.emailslicer;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Core email-slicing logic. Validates and splits an email address into
 * its username and domain components.
 */
public final class EmailSlicer {

    /** Simple regex: at least one non-{@code @} char, then {@code @}, then at least one non-{@code @} char. */
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[^@]+@[^@]+$");

    private EmailSlicer() {
        // utility class — not instantiable
    }

    /**
     * Slices an email address into username and domain parts.
     *
     * <p>The input is trimmed of leading/trailing whitespace before validation.
     * Validation uses the pattern {@code ^[^@]+@[^@]+$}, which requires exactly
     * one {@code @} with at least one character on each side.</p>
     *
     * @param email the raw email string (may include surrounding whitespace)
     * @return an {@link Optional} containing the {@link EmailParts} if valid,
     *         or {@link Optional#empty()} if the input is null or invalid
     */
    public static Optional<EmailParts> slice(String email) {
        if (email == null) {
            return Optional.empty();
        }

        String trimmed = email.trim();

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return Optional.empty();
        }

        int atIndex = trimmed.indexOf('@');
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailParts(username, domain));
    }
}
