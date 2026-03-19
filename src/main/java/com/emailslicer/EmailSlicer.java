package com.emailslicer;

import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Parses and validates email addresses, returning their username and domain components.
 *
 * <p>Validation uses a lightweight regex that ensures:
 * <ul>
 *   <li>Exactly one {@code @} symbol</li>
 *   <li>Non-empty local part (no whitespace)</li>
 *   <li>Non-empty domain with at least one dot (TLD required)</li>
 * </ul>
 */
public final class EmailSlicer {

    /**
     * Regex pattern for basic email validation.
     * Requires: non-empty local part, single {@code @}, domain with at least one dot, no whitespace.
     */
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private EmailSlicer() {
        // Prevent instantiation
    }

    /**
     * Parses the given email string into its username and domain components.
     *
     * <p>Leading and trailing whitespace is trimmed before validation (matching
     * the Python original's {@code strip()} behavior).
     *
     * @param email the raw email string to parse
     * @return an {@link Optional} containing the parsed {@link EmailComponents},
     *         or {@link Optional#empty()} if the input is null or invalid
     */
    public static Optional<EmailComponents> slice(String email) {
        if (email == null) {
            return Optional.empty();
        }

        String trimmed = email.strip();

        if (!EMAIL_PATTERN.matcher(trimmed).matches()) {
            return Optional.empty();
        }

        int atIndex = trimmed.indexOf('@');
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailComponents(username, domain));
    }
}
