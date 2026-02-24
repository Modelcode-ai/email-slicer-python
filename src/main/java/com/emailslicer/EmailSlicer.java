package com.emailslicer;

/**
 * Core logic class for parsing email addresses into username and domain components.
 */
public class EmailSlicer {

    /**
     * Immutable result type holding the parsed username and domain.
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
     * Parses an email address into its username and domain components.
     *
     * @param rawEmail the raw email address string to parse
     * @return a Result containing the username and domain
     * @throws IllegalArgumentException if the email address is invalid
     */
    public Result slice(String rawEmail) {
        if (rawEmail == null) {
            throw new IllegalArgumentException("Email address must not be null");
        }

        String trimmed = rawEmail.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email address must not be empty");
        }

        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            throw new IllegalArgumentException("Email address must contain '@'");
        }

        if (atIndex == 0) {
            throw new IllegalArgumentException("Email address must have a username before '@'");
        }

        if (atIndex == trimmed.length() - 1) {
            throw new IllegalArgumentException("Email address must have a domain after '@'");
        }

        if (trimmed.indexOf('@', atIndex + 1) != -1) {
            throw new IllegalArgumentException("Email address must contain exactly one '@'");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new Result(username, domain);
    }
}
