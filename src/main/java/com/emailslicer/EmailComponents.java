package com.emailslicer;

/**
 * Immutable data carrier for the parsed components of an email address.
 *
 * @param username the local part before the {@code @} symbol
 * @param domain   the domain part after the {@code @} symbol
 */
public record EmailComponents(String username, String domain) {
}
