package com.emailslicer;

/**
 * Core email slicing logic that extracts the username and domain from an email address.
 * <p>
 * This class performs simple validation (presence of '@') and string slicing,
 * mirroring the behavior of the original Python emailSlicer.py script.
 * It intentionally does not perform RFC-compliant email validation.
 */
public final class EmailSlicer {

    /**
     * Immutable result of slicing an email address into username and domain.
     *
     * @param username the part of the email before the first '@'
     * @param domain   the part of the email after the first '@'
     */
    public record Result(String username, String domain) {
    }

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Slices the given raw email string into a username and domain.
     * <p>
     * The input is trimmed of leading and trailing whitespace (mirroring Python's
     * {@code str.strip()}). If the trimmed input does not contain an '@' character,
     * an {@link IllegalArgumentException} is thrown with a user-facing message.
     * <p>
     * When the input contains one or more '@' characters, the split occurs at the
     * <em>first</em> '@', matching the behavior of Python's {@code str.index("@")}.
     *
     * @param rawEmail the raw email string entered by the user (may be {@code null})
     * @return a {@link Result} containing the extracted username and domain
     * @throws IllegalArgumentException if the input is null, blank, or missing '@'
     */
    public static Result slice(String rawEmail) {
        if (rawEmail == null) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String email = rawEmail.trim();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new Result(username, domain);
    }
}
