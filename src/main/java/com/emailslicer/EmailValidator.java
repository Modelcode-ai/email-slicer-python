package com.emailslicer;

/**
 * Utility class for validating email addresses using basic-plus rules.
 *
 * <p>Validation rules:
 * <ul>
 *   <li>Input must not be null or empty (after trimming whitespace)</li>
 *   <li>Must contain exactly one '{@literal @}' character</li>
 *   <li>'{@literal @}' must not be the first or last character</li>
 * </ul>
 *
 * <p>This is intentionally simple and non-RFC-compliant, matching the intent
 * of the original Python Email Slicer tool while providing slightly more
 * robust validation than the original.
 */
public final class EmailValidator {

    private EmailValidator() {
        // utility class — prevent instantiation
    }

    /**
     * Checks whether the given email string is valid for slicing.
     *
     * @param email the email address to validate
     * @return {@code true} if the email passes basic-plus validation rules,
     *         {@code false} otherwise
     */
    public static boolean isValid(String email) {
        if (email == null) {
            return false;
        }

        String trimmed = email.strip();
        if (trimmed.isEmpty()) {
            return false;
        }

        int atIndex = trimmed.indexOf('@');
        int lastAtIndex = trimmed.lastIndexOf('@');

        // Must contain exactly one '@'
        if (atIndex == -1 || atIndex != lastAtIndex) {
            return false;
        }

        // '@' cannot be the first or last character
        if (atIndex == 0 || atIndex == trimmed.length() - 1) {
            return false;
        }

        return true;
    }
}
