package com.emailslicer;

import java.util.Optional;

/**
 * Core email parsing logic. Splits an email address string into its username
 * and domain components based on the first {@code @} character.
 *
 * <p>This class contains no I/O side effects; all console interaction is
 * handled by the {@link Main} CLI entry point.</p>
 */
public class EmailSlicer {

    /**
     * Parses the given raw input string as an email address.
     *
     * <p>Behavior mirrors the original Python implementation:</p>
     * <ul>
     *   <li>Trims leading and trailing whitespace (equivalent to Python's {@code strip()}).</li>
     *   <li>If the trimmed string contains at least one {@code @}, splits on the
     *       <em>first</em> occurrence (equivalent to Python's {@code index("@")}).</li>
     *   <li>Characters before the first {@code @} become the username; characters
     *       after become the domain.</li>
     * </ul>
     *
     * @param rawInput the raw email string, which may be {@code null}
     * @return an {@link Optional} containing the {@link EmailSliceResult} if the
     *         input is valid (contains {@code @}), or {@link Optional#empty()} if
     *         the input is {@code null}, empty, or does not contain {@code @}
     */
    public Optional<EmailSliceResult> slice(String rawInput) {
        if (rawInput == null) {
            return Optional.empty();
        }

        String trimmed = rawInput.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailSliceResult(username, domain));
    }
}
