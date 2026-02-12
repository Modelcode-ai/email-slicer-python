package com.emailslicer;

/**
 * Core email parsing logic that extracts username and domain from an email address.
 * This class expects pre-trimmed input - trimming should be done by the caller.
 */
public final class EmailParser {

    private EmailParser() {
        // Utility class - prevent instantiation
    }

    /**
     * Parses an email address into its username and domain components.
     *
     * @param email the email address to parse (expected to be pre-trimmed)
     * @return a ParsedEmail containing username and domain, or null if the email is invalid (no @ symbol)
     */
    public static ParsedEmail sliceEmail(String email) {
        if (email == null) {
            return null;
        }

        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            return null;
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Value object representing a parsed email with username and domain components.
     */
    public record ParsedEmail(String username, String domain) {
    }
}
