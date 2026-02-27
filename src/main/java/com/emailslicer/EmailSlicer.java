package com.emailslicer;

/**
 * Utility class that parses an email address into its username and domain parts.
 *
 * <p>This class mirrors the behavior of the original Python email slicer script,
 * which uses {@code str.find("@")} and {@code str.index("@")} to locate the
 * {@code @} character, then slices the string into username and domain components.</p>
 *
 * <h3>Validation rules</h3>
 * <ul>
 *   <li>Null, empty, or whitespace-only input is rejected.</li>
 *   <li>Input that does not contain an {@code @} character is rejected.</li>
 *   <li>The first {@code @} is used as the split point (matching Python's
 *       {@code str.index("@")} behavior).</li>
 *   <li>Edge cases such as {@code @example.com} (empty username) or {@code user@}
 *       (empty domain) are permitted, matching Python's permissive string slicing.</li>
 * </ul>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class — prevent instantiation.
    }

    /**
     * Parses the given raw input into an {@link EmailParts} record.
     *
     * <p>Leading and trailing whitespace is trimmed before parsing, mirroring
     * Python's {@code str.strip()} call.</p>
     *
     * @param rawInput the raw email string (may include surrounding whitespace)
     * @return an {@link EmailParts} containing the username and domain
     * @throws IllegalArgumentException if the input is null, blank, or does not
     *                                  contain an {@code @} character
     */
    public static EmailParts slice(String rawInput) {
        if (rawInput == null || rawInput.isBlank()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String email = rawInput.strip();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
