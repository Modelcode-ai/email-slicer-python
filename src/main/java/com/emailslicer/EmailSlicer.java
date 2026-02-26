package com.emailslicer;

/**
 * Pure email parsing and validation logic.
 * <p>
 * Provides static utility methods for minimal email validation (presence of '@')
 * and extraction of username and domain components. This mirrors the slicing
 * semantics of the original Python emailSlicer.py script.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Checks whether the given string is a minimally valid email
     * (i.e., contains at least one '@' character after trimming).
     *
     * @param email the email string to validate (may be null)
     * @return true if the trimmed string is non-empty and contains '@'
     */
    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }
        String trimmed = email.trim();
        return !trimmed.isEmpty() && trimmed.contains("@");
    }

    /**
     * Extracts the username portion of an email address
     * (the substring before the first '@').
     * <p>
     * The caller must first verify the email is valid via {@link #isValid(String)}.
     *
     * @param email a valid email string (must contain '@')
     * @return the username substring before the first '@'
     */
    public static String extractUsername(String email) {
        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');
        return trimmed.substring(0, atIndex);
    }

    /**
     * Extracts the domain portion of an email address
     * (the substring after the first '@').
     * <p>
     * The caller must first verify the email is valid via {@link #isValid(String)}.
     *
     * @param email a valid email string (must contain '@')
     * @return the domain substring after the first '@'
     */
    public static String extractDomain(String email) {
        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');
        return trimmed.substring(atIndex + 1);
    }
}
