package com.emailslicer;

/**
 * Pure parsing logic for slicing an email address into username and domain.
 * <p>
 * This class has no I/O dependencies and is designed to be easily unit-tested.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation
    }

    /**
     * Slices an email address into its username and domain components.
     * <p>
     * The input is trimmed of leading/trailing whitespace (matching the original
     * Python tool's {@code strip()} behavior). Validation checks that the input
     * contains an {@code @} symbol; if not, an {@link IllegalArgumentException}
     * is thrown.
     *
     * @param email the email address to slice
     * @return an {@link EmailSliceResult} containing the username and domain
     * @throws IllegalArgumentException if the email is null, blank, or does not contain {@code @}
     */
    public static EmailSliceResult slice(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String trimmed = email.strip();

        if (!trimmed.contains("@")) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        int atIndex = trimmed.indexOf("@");
        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailSliceResult(username, domain);
    }
}
