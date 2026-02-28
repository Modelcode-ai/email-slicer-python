package com.emailslicer;

/**
 * Immutable value type holding the parsed components of an email address.
 *
 * @param username the part before the first {@code @} symbol
 * @param domain   the part after the first {@code @} symbol
 */
public record EmailParts(String username, String domain) {}
