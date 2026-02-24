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
        // TODO: implement in next task
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
