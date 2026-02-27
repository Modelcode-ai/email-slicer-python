package com.emailslicer;

/**
 * Pure logic class that validates and parses an email address into its
 * username and domain components. Contains no console I/O.
 */
public class EmailSlicer {

    /**
     * Immutable result holder for a parsed email address.
     *
     * @param username the part before the first {@code @}
     * @param domain   the part after the first {@code @}
     */
    public record EmailParts(String username, String domain) { }

    /**
     * Trims whitespace from the input, validates that it contains an
     * {@code @} character, and splits it into username and domain based
     * on the first {@code @}.
     *
     * @param email the raw email string (may include leading/trailing whitespace)
     * @return an {@link EmailParts} record with the parsed username and domain
     * @throws IllegalArgumentException if the trimmed input does not contain {@code @}
     */
    public EmailParts slice(String email) {
        String trimmed = email.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
