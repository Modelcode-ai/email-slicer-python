package emailslicer;

/**
 * Immutable value object holding the parsed parts of an email address.
 *
 * @param username the local part of the email (before the first {@code @})
 * @param domain   the domain part of the email (after the first {@code @})
 */
public record EmailParts(String username, String domain) {
}
