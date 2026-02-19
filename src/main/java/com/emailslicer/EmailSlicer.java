package com.emailslicer;

/**
 * Utility class for parsing email addresses into their username and domain components.
 *
 * <p>This is the Java 17 port of the original Python Email Slicer tool. It provides
 * stricter validation than the Python version: null/empty inputs, missing {@code @},
 * multiple {@code @} characters, and empty username or domain portions are all rejected
 * with an {@link IllegalArgumentException}.</p>
 *
 * <p>Usage example:</p>
 * <pre>{@code
 *     EmailParts parts = EmailSlicer.parse("user@example.com");
 *     System.out.println(parts.username()); // "user"
 *     System.out.println(parts.domain());   // "example.com"
 * }</pre>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Prevent instantiation
    }

    /**
     * Parses an email address string into its username and domain components.
     *
     * <p>The input is trimmed of leading and trailing whitespace before validation.
     * The method enforces the following rules:</p>
     * <ul>
     *     <li>Input must not be {@code null} or blank.</li>
     *     <li>Input must contain exactly one {@code @} character.</li>
     *     <li>The username (portion before {@code @}) must not be empty.</li>
     *     <li>The domain (portion after {@code @}) must not be empty.</li>
     * </ul>
     *
     * @param email the email address to parse
     * @return an {@link EmailParts} instance containing the username and domain
     * @throws IllegalArgumentException if the input is null, blank, or not a valid email structure
     */
    public static EmailParts parse(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email address must not be null or empty.");
        }

        String trimmed = email.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email: missing '@' symbol.");
        }

        if (atIndex != trimmed.lastIndexOf('@')) {
            throw new IllegalArgumentException("Invalid email: multiple '@' symbols found.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Invalid email: username portion is empty.");
        }

        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Invalid email: domain portion is empty.");
        }

        return new EmailParts(username, domain);
    }
}
