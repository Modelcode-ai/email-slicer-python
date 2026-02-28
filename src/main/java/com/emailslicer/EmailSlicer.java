package com.emailslicer;

/**
 * Utility class that parses an email address string into its username and domain parts.
 * <p>
 * This replicates the parsing logic of the original Python {@code emailSlicer.py} script:
 * trimming whitespace, locating the first {@code @} via {@link String#indexOf(int)},
 * and splitting into username and domain substrings.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Prevent instantiation
    }

    /**
     * Parses an email string into its username and domain components.
     * <p>
     * The email is trimmed of leading/trailing whitespace, then split at the
     * <b>first</b> {@code @} character. If the input does not contain {@code @},
     * the method returns {@code null}.
     *
     * @param email the email address to parse; must not be {@code null}
     * @return an {@link EmailParts} record containing the username and domain,
     *         or {@code null} if the input does not contain {@code @}
     * @throws IllegalArgumentException if {@code email} is {@code null}
     */
    public static EmailParts slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');
        if (atIndex < 0) {
            return null;
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
