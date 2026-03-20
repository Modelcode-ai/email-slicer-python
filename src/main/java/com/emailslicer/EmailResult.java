package com.emailslicer;

/**
 * Immutable value type holding the parsed components of an email address.
 *
 * @param username the local part before the {@code @} symbol
 * @param domain   the domain part after the {@code @} symbol
 */
public record EmailResult(String username, String domain) {
}
