package com.emailslicer;

/**
 * Core email parsing and validation logic.
 *
 * <p>This class encapsulates the email slicing behavior ported from the original
 * Python {@code emailSlicer.py} script. It is independent of console I/O and
 * can be tested in isolation.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Parses a raw email string into its username and domain components.
     *
     * <p>The input is trimmed of leading and trailing whitespace. The email is
     * split on the first {@code @} character: everything before it becomes the
     * username and everything after it becomes the domain.</p>
     *
     * @param rawEmail the raw email address string to parse
     * @return a {@link ParsedEmail} record containing the username and domain
     * @throws IllegalArgumentException if {@code rawEmail} is {@code null},
     *         blank after trimming, or does not contain an {@code @} character
     */
    public static ParsedEmail parse(String rawEmail) {
        if (rawEmail == null) {
            throw new IllegalArgumentException("Email address must not be null.");
        }

        String email = rawEmail.strip();

        if (email.isEmpty()) {
            throw new IllegalArgumentException("Email address must not be empty.");
        }

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException(
                    "Invalid email address. It must contain an '@' character.");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new ParsedEmail(username, domain);
    }
}
