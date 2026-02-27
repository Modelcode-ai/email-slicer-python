package com.emailslicer;

/**
 * Core email parsing logic that validates and splits an email address
 * into username and domain components.
 *
 * <p>This class mirrors the behavior of the original Python emailSlicer.py:
 * it trims whitespace, checks for the presence of '@', and splits at the
 * first occurrence of '@'.</p>
 *
 * <p>Error signaling: {@link #parse(String)} returns {@code null} for invalid
 * input (no '@' present, empty, or null). This is the simplest approach for
 * this small CLI application.</p>
 */
public final class EmailSlicer {

    /**
     * Holds the parsed username and domain from a valid email address.
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
     * Parses an email address into username and domain components.
     *
     * <p>The input is trimmed of leading/trailing whitespace. If the trimmed
     * input contains '@', it is split at the <b>first</b> occurrence:
     * everything before becomes the username, everything after becomes the
     * domain (which may itself contain additional '@' characters).</p>
     *
     * @param rawEmail the raw email string; may be {@code null}
     * @return a {@link Result} with username and domain if valid,
     *         or {@code null} if the input is null, empty, or missing '@'
     */
    public Result parse(String rawEmail) {
        String trimmed = (rawEmail == null) ? "" : rawEmail.trim();

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            return null;
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);
        return new Result(username, domain);
    }
}
