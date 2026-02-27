package com.emailslicer;

/**
 * Immutable record representing the parsed components of an email address.
 *
 * @param username the local part before the {@code @} symbol
 * @param domain   the domain part after the {@code @} symbol
 */
public record EmailParts(String username, String domain) { }
