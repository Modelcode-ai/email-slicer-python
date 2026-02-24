package emailslicer;

/**
 * Immutable value object representing the result of parsing an email address.
 * <p>
 * A result is either valid (containing a username and domain) or invalid
 * (indicating the input did not contain an '@' character).
 */
public class EmailSliceResult {

    private final boolean valid;
    private final String username;
    private final String domain;

    private EmailSliceResult(boolean valid, String username, String domain) {
        this.valid = valid;
        this.username = username;
        this.domain = domain;
    }

    /**
     * Creates an invalid result, indicating the email could not be parsed.
     *
     * @return an invalid {@code EmailSliceResult}
     */
    public static EmailSliceResult invalid() {
        return new EmailSliceResult(false, null, null);
    }

    /**
     * Creates a valid result with the given username and domain.
     *
     * @param username the part of the email before the first '@'
     * @param domain   the part of the email after the first '@'
     * @return a valid {@code EmailSliceResult}
     */
    public static EmailSliceResult valid(String username, String domain) {
        return new EmailSliceResult(true, username, domain);
    }

    /**
     * Returns whether this result represents a valid email parse.
     *
     * @return {@code true} if the email was successfully parsed
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Returns the username (substring before the first '@').
     *
     * @return the username, or {@code null} if the result is invalid
     */
    public String getUsername() {
        return username;
    }

    /**
     * Returns the domain (substring after the first '@').
     *
     * @return the domain, or {@code null} if the result is invalid
     */
    public String getDomain() {
        return domain;
    }
}
