package com.emailslicer;

/**
 * Immutable value object holding the parsed username and domain parts of an email address.
 *
 * @param username the local part of the email address (before the {@code @} symbol)
 * @param domain   the domain part of the email address (after the {@code @} symbol)
 */
public record EmailParts(String username, String domain) {
}
