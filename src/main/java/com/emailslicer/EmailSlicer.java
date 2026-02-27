package com.emailslicer;

/**
 * Pure email parsing and validation logic.
 * Splits an email address into username and domain components
 * using the first {@code @} character as the delimiter.
 */
public final class EmailSlicer {

    /**
     * Holds the parsed username and domain from an email address.
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

    private EmailSlicer() {
        // Utility class; prevent instantiation
    }

    /**
     * Parses an email string into its username and domain components.
     *
     * <p>The input is trimmed of leading/trailing whitespace. The split occurs
     * at the first {@code @} character; everything before it is the username,
     * everything after it is the domain. Empty username or domain values are
     * permitted to preserve the original Python behavior.</p>
     *
     * @param email the email address to parse
     * @return a {@link Result} containing the username and domain
     * @throws IllegalArgumentException if email is null or does not contain {@code @}
     */
    public static Result parse(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.trim();
        int atIndex = trimmed.indexOf('@');
        if (atIndex < 0) {
            throw new IllegalArgumentException("Invalid email: missing '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        // Preserve original behavior: allow empty username or domain if present in input
        return new Result(username, domain);
    }
}
