package com.emailslicer;

/**
 * Immutable data carrier holding the username and domain parts of an email address.
 *
 * @param username the local part before the {@code @} symbol
 * @param domain   the domain part after the {@code @} symbol
 */
public record EmailParts(String username, String domain) {
}
