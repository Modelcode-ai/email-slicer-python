package com.emailslicer;

import java.util.Optional;

/**
 * Email Slicer — parses an email address into its username and domain parts.
 *
 * <p>Migrated from the Python {@code emailSlicer.py} script. Preserves the
 * original behaviour: trim whitespace, split on the first {@code @} symbol,
 * and treat anything without {@code @} as invalid.</p>
 */
public class EmailSlicer {

    /**
     * Parses a raw email string into its username and domain components.
     *
     * <p>The input is trimmed of leading/trailing whitespace. The first
     * {@code @} character is used as the split point — if multiple {@code @}
     * characters are present, only the first one is used (matching the
     * behaviour of Python's {@code str.index('@')}).</p>
     *
     * @param rawInput the raw email string (may include surrounding whitespace)
     * @return an {@link Optional} containing the parsed {@link EmailParts},
     *         or {@link Optional#empty()} if the input is blank or contains
     *         no {@code @} symbol
     */
    static Optional<EmailParts> parseEmail(String rawInput) {
        if (rawInput == null) {
            return Optional.empty();
        }

        String trimmed = rawInput.trim();
        if (trimmed.isEmpty()) {
            return Optional.empty();
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex < 0) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);
        return Optional.of(new EmailParts(username, domain));
    }

    public static void main(String[] args) {
        // CLI entry point — to be implemented
    }
}
