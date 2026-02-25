package com.emailslicer;

import java.util.Optional;

/**
 * Core email parsing and validation logic.
 *
 * <p>Encapsulates whitespace trimming, validation, and splitting of an email
 * address into its username and domain components.</p>
 *
 * <h3>Validation rules</h3>
 * <ul>
 *   <li>The input is trimmed of leading and trailing whitespace (equivalent
 *       to Python's {@code str.strip()}).</li>
 *   <li>The trimmed string must contain exactly one {@code @} character.</li>
 *   <li>The {@code @} must not be the first character.</li>
 *   <li>The {@code @} must not be the last character.</li>
 * </ul>
 *
 * <p>If validation fails, {@link #slice(String)} returns an empty
 * {@link Optional}. Otherwise it returns an {@link Optional} containing an
 * {@link EmailParts} value with the username and domain.</p>
 */
public class EmailSlicer {

    /**
     * Parses the given email string into its username and domain parts.
     *
     * @param email the raw email input (may include leading/trailing whitespace)
     * @return an {@link Optional} containing the parsed {@link EmailParts},
     *         or {@link Optional#empty()} if the input is invalid
     */
    public Optional<EmailParts> slice(String email) {
        if (email == null) {
            return Optional.empty();
        }

        String trimmed = email.strip();

        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        // Count occurrences of '@' — must be exactly one
        long atCount = trimmed.chars().filter(ch -> ch == '@').count();
        if (atCount != 1) {
            return Optional.empty();
        }

        int atIndex = trimmed.indexOf('@');

        // '@' must not be at the first or last position
        if (atIndex == 0 || atIndex == trimmed.length() - 1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailParts(username, domain));
    }
}
