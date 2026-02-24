package com.emailslicer;

/**
 * Utility class for parsing email addresses into username and domain components.
 * <p>
 * Splits the email on the first {@code @} character. Characters before the
 * {@code @} become the username; characters after become the domain.
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Prevent instantiation
    }

    /**
     * Parses a raw email input into its username and domain parts.
     *
     * @param rawInput the email address string (may include leading/trailing whitespace)
     * @return a {@link Result} containing the username and domain
     * @throws IllegalArgumentException if the input is null, empty, whitespace-only,
     *                                  or does not contain an {@code @} character
     */
    public static Result slice(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String trimmed = rawInput.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Invalid email address");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Invalid email address");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new Result(username, domain);
    }

    /**
     * Holds the parsed result of an email address: the username and domain.
     */
    public static final class Result {

        private final String username;
        private final String domain;

        public Result(String username, String domain) {
            this.username = username;
            this.domain = domain;
        }

        public String getUsername() {
            return username;
        }

        public String getDomain() {
            return domain;
        }
    }
}
