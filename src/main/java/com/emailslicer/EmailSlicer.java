package com.emailslicer;

/**
 * Core email parsing logic that splits an email address into username and domain
 * components by locating the first '@' character.
 *
 * <p>This class mirrors the behavior of the original Python email slicer tool,
 * which uses {@code index("@")} and string slicing to extract the parts.</p>
 */
public final class EmailSlicer {

    /**
     * Immutable result holding the parsed username and domain from an email address.
     *
     * @param username the portion of the email before the first '@'
     * @param domain   the portion of the email after the first '@'
     */
    public record Result(String username, String domain) {}

    /**
     * Parses the given raw input string as an email address by splitting at the
     * first '@' character.
     *
     * <p>The input is trimmed of leading and trailing whitespace before parsing.
     * If the trimmed input contains at least one '@', the method returns a
     * {@link Result} with the username (before the first '@') and domain
     * (after the first '@'). If no '@' is found, the method returns {@code null}.</p>
     *
     * @param rawInput the raw email string to parse
     * @return a {@link Result} containing username and domain, or {@code null}
     *         if the input does not contain an '@' character
     * @throws IllegalArgumentException if rawInput is {@code null}
     */
    public Result slice(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        String email = rawInput.trim();
        int atIndex = email.indexOf('@');

        if (atIndex < 0) {
            return null;
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);
        return new Result(username, domain);
    }
}
