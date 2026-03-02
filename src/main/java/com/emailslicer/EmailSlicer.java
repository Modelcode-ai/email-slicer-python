package com.emailslicer;

/**
 * Parses an email address string into its username and domain components.
 * <p>
 * The parsing logic mirrors the behaviour of the original Python script:
 * <ul>
 *   <li>Leading and trailing whitespace is stripped (equivalent to Python {@code str.strip()}).</li>
 *   <li>The email is split on the <em>first</em> {@code @} character.</li>
 *   <li>If no {@code @} is present, the input is considered invalid.</li>
 * </ul>
 */
public class EmailSlicer {

    /**
     * Parses a raw email input string into an {@link EmailParts} record.
     *
     * @param rawInput the raw email string, possibly with surrounding whitespace
     * @return an {@link EmailParts} containing the username and domain
     * @throws IllegalArgumentException if the trimmed input does not contain {@code @}
     */
    public EmailParts parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Input must not be null");
        }

        String trimmed = rawInput.strip();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Please enter a valid Email Id.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
