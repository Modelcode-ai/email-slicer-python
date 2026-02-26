package com.emailslicer;

/**
 * Pure parsing logic for splitting an email address into username and domain.
 *
 * <p>Migrated from the Python {@code emailSlicer.py} script. Trims whitespace,
 * validates the presence of {@code @}, and splits at the first {@code @}.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class – not instantiable
    }

    /**
     * Parses a raw email input string into its username and domain components.
     *
     * <p>The input is stripped of leading/trailing whitespace (using
     * {@link String#strip()}, which mirrors Python's {@code str.strip()}).
     * The string is then split at the <em>first</em> {@code @} character.</p>
     *
     * @param rawInput the raw email string (may include surrounding whitespace)
     * @return an {@link EmailParts} record containing the username and domain
     * @throws InvalidEmailException if {@code rawInput} is {@code null},
     *         empty/whitespace-only after stripping, or does not contain {@code @}
     */
    public static EmailParts slice(String rawInput) throws InvalidEmailException {
        if (rawInput == null) {
            throw new InvalidEmailException("Input is null");
        }

        String trimmed = rawInput.strip();

        if (trimmed.isEmpty()) {
            throw new InvalidEmailException("Input is empty or whitespace-only");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new InvalidEmailException("Input does not contain '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }

    /**
     * Immutable record holding the parsed username and domain of an email address.
     */
    public record EmailParts(String username, String domain) { }
}
