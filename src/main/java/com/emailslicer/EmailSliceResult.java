package com.emailslicer;

/**
 * Immutable data carrier holding the parsed components of an email address.
 *
 * @param username the part of the email before the first {@code @} character
 * @param domain   the part of the email after the first {@code @} character
 */
public record EmailSliceResult(String username, String domain) {
}
