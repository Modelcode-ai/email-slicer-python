package com.emailslicer;

/**
 * Core email parsing logic. Splits an email address into username and domain
 * components based on the first {@code @} character.
 *
 * <p>This class contains no I/O; it is a pure parsing utility.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — not instantiable
    }

    /**
     * Parses a raw email string into its username and domain parts.
     *
     * @param rawInput the raw email string (may include leading/trailing whitespace)
     * @return a {@link ParsedEmail} containing the username and domain
     * @throws IllegalArgumentException if the input does not contain an {@code @} character
     */
    public static ParsedEmail parse(String rawInput) {
        // placeholder — implementation in next task
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Holds the parsed username and domain of an email address.
     */
    public record ParsedEmail(String username, String domain) { }
}
