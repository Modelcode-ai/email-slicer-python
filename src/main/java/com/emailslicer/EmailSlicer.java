package com.emailslicer;

/**
 * Core email parsing logic.
 * <p>
 * Validates that the input contains an {@code @} character and splits
 * it into username (local part) and domain. The split is performed at
 * the <em>first</em> {@code @} character to preserve behavioral parity
 * with the original Python implementation which uses {@code str.index("@")}.
 * <p>
 * Leading and trailing whitespace is trimmed before validation,
 * mirroring Python's {@code str.strip()} semantics.
 */
public class EmailSlicer {

    /**
     * Parses the given raw input string into its email parts.
     *
     * @param rawInput the raw email string (may include surrounding whitespace)
     * @return an {@link EmailParts} record containing the username and domain
     * @throws InvalidEmailException if the trimmed input does not contain {@code @}
     */
    public EmailParts parse(String rawInput) {
        String email = rawInput.strip();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new InvalidEmailException("Email address must contain an '@' character.");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
