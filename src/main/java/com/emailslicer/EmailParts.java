package com.emailslicer;

/**
 * A value object holding the parsed parts of an email address.
 *
 * @param username the local part before the {@code @} character
 * @param domain   the domain part after the {@code @} character
 */
public record EmailParts(String username, String domain) {
}
