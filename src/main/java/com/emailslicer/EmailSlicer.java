package com.emailslicer;

/**
 * Core email parsing and validation logic.
 * <p>
 * Accepts a raw email string, trims whitespace, validates the presence of
 * an {@code @} separator with non-empty username and domain parts, and
 * returns a {@link Result} containing the parsed components.
 * <p>
 * Splitting uses the <em>first</em> {@code @} character, matching the
 * behavior of Python's {@code str.index("@")}.
 */
public class EmailSlicer {

    /**
     * Immutable result holding the parsed username and domain.
     */
    public static class Result {
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

    /**
     * Parses an email string into its username and domain components.
     *
     * @param email the raw email string (may contain leading/trailing whitespace)
     * @return a {@link Result} containing the username and domain
     * @throws IllegalArgumentException if the email is null, empty after trimming,
     *                                  missing an {@code @} symbol, or has an empty
     *                                  username or domain part
     */
    public Result slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an '@' symbol");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        if (username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (domain.isEmpty()) {
            throw new IllegalArgumentException("Domain must not be empty");
        }

        return new Result(username, domain);
    }
}
