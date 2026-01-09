package com.example.emailslicer;

/**
 * Core business logic for parsing and validating email addresses.
 * This class provides a single static method to parse email strings
 * into username and domain components.
 */
public final class EmailParser {

    /**
     * Immutable result type containing the parsed components of an
     * email address.
     *
     * @param username The part of the email before the '@' symbol
     * @param domain The part of the email after the '@' symbol
     */
    public record EmailParts(String username, String domain) {
    }

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EmailParser() {
        // Prevent instantiation
    }

    /**
     * Parses and validates an email address string, extracting the
     * username and domain.
     *
     * <p>The input is trimmed of leading and trailing whitespace
     * before processing. The email must contain exactly one '@' symbol,
     * with non-empty username and domain components.</p>
     *
     * <p>Examples:</p>
     * <ul>
     *   <li>{@code parseEmail("user@domain.com")} returns
     *   username="user", domain="domain.com"</li>
     *   <li>{@code parseEmail("  user@domain.com  ")} returns
     *   username="user", domain="domain.com"</li>
     *   <li>{@code parseEmail("User.Name@Sub.Domain.org")} returns
     *   username="User.Name", domain="Sub.Domain.org"</li>
     * </ul>
     *
     * @param rawInput the email address string to parse (may contain
     *                 leading/trailing whitespace)
     * @return an {@link EmailParts} instance containing the extracted
     *         username and domain
     * @throws IllegalArgumentException if the input is null, empty/blank
     *         after trimming, does not contain exactly one '@' symbol,
     *         or has an empty username or domain component
     */
    public static EmailParts parseEmail(final String rawInput) {
        // Validate null input
        if (rawInput == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        // Trim leading and trailing whitespace (mirroring Python's strip())
        String email = rawInput.strip();

        // Validate blank input after trimming
        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Find the position of the '@' symbol
        int atIndex = email.indexOf("@");

        // Validate presence of '@' symbol
        if (atIndex == -1) {
            throw new IllegalArgumentException(
                    "Email must contain an @ symbol");
        }

        // Check for multiple '@' symbols
        int lastAtIndex = email.lastIndexOf("@");
        if (atIndex != lastAtIndex) {
            throw new IllegalArgumentException(
                    "Email must contain exactly one @ symbol");
        }

        // Extract username (substring before '@')
        String username = email.substring(0, atIndex);

        // Validate non-empty username
        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // Extract domain (substring after '@')
        String domain = email.substring(atIndex + 1);

        // Validate non-empty domain
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain cannot be empty");
        }

        // Return the parsed components
        return new EmailParts(username, domain);
    }
}
