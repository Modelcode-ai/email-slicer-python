package com.emailslicer;

/**
 * Email Slicer - A simple CLI tool that parses email addresses.
 *
 * This tool takes an email address as input and returns the username
 * (local part before @) and domain (part after @) as output.
 */
public class EmailSlicer {

    /**
     * Immutable data model representing a parsed email address.
     *
     * @param username the local part before the @ symbol
     * @param domain the part after the @ symbol
     */
    public record ParsedEmail(String username, String domain) {}

    /**
     * Parses an email address into username and domain components.
     *
     * <p>This method validates the email structure and extracts the username
     * (local part before @) and domain (part after @).
     *
     * @param email the email address to parse (should be pre-stripped of whitespace)
     * @return a ParsedEmail record containing the username and domain
     * @throws IllegalArgumentException if the email is invalid
     */
    public static ParsedEmail parseEmail(String email) {
        // Validate null or blank input
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email must not be empty");
        }

        // Find first and last occurrence of @
        int firstAt = email.indexOf('@');
        int lastAt = email.lastIndexOf('@');

        // Validate exactly one @ symbol
        if (firstAt == -1) {
            throw new IllegalArgumentException("email must contain exactly one '@'");
        }

        if (firstAt != lastAt) {
            throw new IllegalArgumentException("email must contain exactly one '@'");
        }

        // Validate non-empty username (before @)
        if (firstAt == 0) {
            throw new IllegalArgumentException("username part must not be empty");
        }

        // Validate non-empty domain (after @)
        if (firstAt == email.length() - 1) {
            throw new IllegalArgumentException("domain part must not be empty");
        }

        // Extract username and domain using substring
        String username = email.substring(0, firstAt);
        String domain = email.substring(firstAt + 1);

        return new ParsedEmail(username, domain);
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Email Slicer - Java 17 (placeholder)");
    }
}
