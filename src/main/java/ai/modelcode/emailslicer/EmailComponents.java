package ai.modelcode.emailslicer;

/**
 * Immutable representation of a sliced email address, carrying the username
 * (the part before {@code @}) and the domain (the part after {@code @}).
 *
 * @param username the local part of the email address before the {@code @} symbol
 * @param domain   the domain part of the email address after the {@code @} symbol
 */
public record EmailComponents(String username, String domain) { }
