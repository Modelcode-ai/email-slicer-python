package com.emailslicer;

/**
 * Core email parsing and validation logic.
 * <p>
 * Takes a raw email string, trims whitespace, validates that it contains
 * an {@code @} character, and splits into username and domain components.
 * The split is performed on the <em>first</em> {@code @} character, so inputs
 * with multiple {@code @} signs are handled consistently.
 */
public final class EmailSlicer {

    /**
     * Immutable result holding the parsed username and domain.
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

    /**
     * Parses a raw email string into its username and domain components.
     *
     * <ol>
     *   <li>Trims leading and trailing whitespace.</li>
     *   <li>Validates that the trimmed input is non-empty and contains {@code @}.</li>
     *   <li>Splits on the first {@code @} to extract username and domain.</li>
     * </ol>
     *
     * @param rawInput the raw email string (may include surrounding whitespace)
     * @return a {@link Result} with the extracted username and domain
     * @throws IllegalArgumentException if the input is null, empty after trimming,
     *                                  or does not contain {@code @}
     */
    public Result parse(String rawInput) {
        if (rawInput == null) {
            throw new IllegalArgumentException("Email input must not be null.");
        }

        String trimmed = rawInput.strip();

        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Email input must not be empty.");
        }

        int atIndex = trimmed.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("Email must contain '@'.");
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return new Result(username, domain);
    }
}
