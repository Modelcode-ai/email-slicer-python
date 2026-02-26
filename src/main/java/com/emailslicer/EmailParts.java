package com.emailslicer;

/**
 * Immutable value type representing the username and domain components of an email address.
 *
 * @param username the portion of the email before the first {@code @} character
 * @param domain   the portion of the email after the first {@code @} character
 */
public record EmailParts(String username, String domain) {
}
