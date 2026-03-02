package com.emailslicer;

/**
 * Utility class that parses an email address string into its username and domain
 * components by splitting at the first {@code @} character.
 *
 * <p>This replicates the behavior of the original Python Email Slicer tool,
 * which uses {@code index("@")} and string slicing to extract the parts.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Utility class; prevent instantiation
    }

    /**
     * Slices the given raw input into username and domain parts.
     *
     * <p>The input is trimmed of leading and trailing whitespace (equivalent to
     * Python's {@code strip()}). If the trimmed input contains an {@code @}
     * character, the first occurrence is used as the split point. Otherwise,
     * an {@link InvalidEmailException} is thrown.</p>
     *
     * @param rawInput the raw email string (may be {@code null})
     * @return an {@link EmailSliceResult} with username and domain (never {@code null})
     * @throws InvalidEmailException if {@code rawInput} is {@code null} or does not
     *                               contain an {@code @} after trimming
     */
    public static EmailSliceResult slice(String rawInput) {
        if (rawInput == null) {
            throw new InvalidEmailException("Input is null");
        }

        String trimmed = rawInput.trim();
        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new InvalidEmailException("No '@' character found in input");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new EmailSliceResult(username, domain);
    }
}
