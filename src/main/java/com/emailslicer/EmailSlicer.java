package com.emailslicer;

/**
 * Parses an email address into its username and domain components.
 * <p>
 * This class mirrors the parsing semantics of the original Python Email Slicer:
 * <ul>
 *   <li>Trims leading and trailing whitespace (equivalent to Python's {@code str.strip()})</li>
 *   <li>Validates that the email contains at least one {@code @} character</li>
 *   <li>Splits at the <em>first</em> {@code @} — everything before is the username,
 *       everything after is the domain</li>
 * </ul>
 */
public class EmailSlicer {

    /**
     * Immutable record representing the parsed parts of an email address.
     *
     * @param username the portion before the first {@code @}
     * @param domain   the portion after the first {@code @}
     */
    public record EmailParts(String username, String domain) {}

    /**
     * Parses a raw email string into its username and domain components.
     *
     * @param rawEmail raw email string, may contain leading/trailing whitespace
     * @return an {@link EmailParts} record with the parsed username and domain
     * @throws IllegalArgumentException if {@code rawEmail} is {@code null}
     *                                  or does not contain {@code @}
     */
    public EmailParts slice(String rawEmail) {
        if (rawEmail == null) {
            throw new IllegalArgumentException("email must not be null");
        }

        String email = rawEmail.strip();

        int atIndex = email.indexOf('@');
        if (atIndex == -1) {
            throw new IllegalArgumentException("email must contain '@'");
        }

        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailParts(username, domain);
    }
}
