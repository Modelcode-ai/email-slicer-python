package com.emailslicer;

/**
 * Immutable value object representing a parsed email address.
 *
 * @param username the portion of the email before the {@code @} symbol
 * @param domain   the portion of the email after the {@code @} symbol
 */
public record EmailParts(String username, String domain) {
}
