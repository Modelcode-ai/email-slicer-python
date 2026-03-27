package com.emailslicer;

/**
 * Immutable record holding the username and domain parts of an email address.
 *
 * @param username the part before the {@code @} symbol
 * @param domain   the part after the {@code @} symbol
 */
public record EmailParts(String username, String domain) {}
