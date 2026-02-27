package com.emailslicer;

/**
 * Immutable record representing the parsed components of an email address.
 *
 * @param username the part before the first {@code @} character
 * @param domain   the part after the first {@code @} character
 */
public record EmailParts(String username, String domain) { }
