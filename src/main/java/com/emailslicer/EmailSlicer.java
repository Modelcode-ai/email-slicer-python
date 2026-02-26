package com.emailslicer;

import java.util.Optional;

/**
 * Stateless utility class that parses a raw email string into its username and domain components.
 *
 * <p>This class encapsulates the validation and splitting logic originally found in the Python
 * {@code emailSlicer.py} script. It trims whitespace, checks for the presence of {@code @},
 * and splits at the first {@code @} character.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not meant to be instantiated
    }

    /**
     * Parses a raw email input string into an {@link EmailParts} result.
     *
     * <p>The method trims leading/trailing whitespace, then checks whether the trimmed string
     * contains an {@code @} character. If it does, the string is split at the first {@code @}:
     * everything before it becomes the username, everything after becomes the domain.
     * Empty username or domain values are permitted (e.g., {@code "@domain.com"} or {@code "user@"}).</p>
     *
     * @param rawInput the raw email string to parse; may be {@code null}
     * @return an {@link Optional} containing the parsed {@link EmailParts} if valid,
     *         or {@link Optional#empty()} if the input is {@code null}, empty, or lacks an {@code @}
     */
    public static Optional<EmailParts> parse(String rawInput) {
        if (rawInput == null) {
            return Optional.empty();
        }

        String trimmed = rawInput.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailParts(username, domain));
    }
}
