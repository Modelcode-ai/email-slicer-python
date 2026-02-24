package com.emailslicer;

/**
 * Utility class that parses an email address into its username and domain
 * components using the first {@code @} character as a separator.
 *
 * <p>This mirrors the parsing semantics of the original Python
 * {@code emailSlicer.py} script: the input is trimmed, checked for the
 * presence of {@code @}, and split at the first occurrence.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // prevent instantiation
    }

    /**
     * Parses the given email string into its username and domain parts.
     *
     * <p>The method trims leading/trailing whitespace, locates the first
     * {@code @} character, and returns an {@link EmailParts} record with
     * the username (everything before the first {@code @}) and the domain
     * (everything after the first {@code @}).</p>
     *
     * @param email the raw email address string
     * @return an {@link EmailParts} containing the parsed username and domain
     * @throws IllegalArgumentException if {@code email} is {@code null} or
     *                                  does not contain an {@code @} character
     */
    public static EmailParts slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
