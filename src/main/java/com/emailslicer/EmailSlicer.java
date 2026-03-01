package com.emailslicer;

/**
 * Parses an email address into its username and domain components.
 * <p>
 * Mirrors the behavior of the original Python emailSlicer.py script:
 * trims whitespace, checks for the presence of {@code @}, and slices
 * on the first {@code @} to extract username and domain.
 */
public class EmailSlicer {

    /**
     * Holds the result of slicing an email address into username and domain.
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
     * Slices an email address into username and domain components.
     * <p>
     * The input is trimmed of leading and trailing whitespace (mirroring
     * Python's {@code strip()}). If the trimmed input contains at least one
     * {@code @} character, the substring before the first {@code @} is the
     * username and the substring after is the domain. This mirrors the
     * Python script's behavior exactly, including for inputs with multiple
     * {@code @} characters.
     *
     * @param email the email address to slice
     * @return a {@link Result} containing the username and domain
     * @throws IllegalArgumentException if the input is null or does not
     *         contain an {@code @} character after trimming
     */
    public Result slice(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email must not be null");
        }

        String trimmed = email.strip();

        int atIndex = trimmed.indexOf("@");
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain an '@' character");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new Result(username, domain);
    }
}
