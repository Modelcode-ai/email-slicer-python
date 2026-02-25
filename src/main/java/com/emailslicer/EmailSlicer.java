package com.emailslicer;

/**
 * Stateless service that parses an email address into its username and domain components.
 *
 * <p>This class replicates the parsing behavior of the original Python
 * {@code emailSlicer.py} script: it trims whitespace, validates the presence
 * of an {@code @} character, and splits at the first {@code @}.</p>
 */
public class EmailSlicer {

    /**
     * Parses the given email string into an {@link EmailSliceResult}.
     *
     * <p>The input is trimmed of leading and trailing whitespace before
     * validation. If the trimmed input does not contain an {@code @}
     * character, an {@link IllegalArgumentException} is thrown.</p>
     *
     * <p>When multiple {@code @} characters are present, the split occurs
     * at the <em>first</em> occurrence (matching the Python
     * {@code str.index("@")} behavior).</p>
     *
     * @param email the raw email string to parse (may include surrounding whitespace)
     * @return an {@link EmailSliceResult} containing the username and domain
     * @throws IllegalArgumentException if the trimmed input is null or does not contain {@code @}
     */
    public EmailSliceResult slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Invalid email");
        }

        String trimmed = email.strip();
        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailSliceResult(username, domain);
    }
}
