package emailslicer;

/**
 * Immutable value type holding the parsed username and domain from an email address.
 *
 * @param username the part of the email before the first {@code @}
 * @param domain   the part of the email after the first {@code @}
 */
public record EmailParts(String username, String domain) {}
