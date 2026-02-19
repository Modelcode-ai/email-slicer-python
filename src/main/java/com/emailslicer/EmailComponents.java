package com.emailslicer;

/**
 * Immutable representation of a parsed email address, split into
 * its username and domain components.
 *
 * @param username the part of the email before the first {@code @} symbol
 * @param domain   the part of the email after the first {@code @} symbol
 */
public record EmailComponents(String username, String domain) {
}
