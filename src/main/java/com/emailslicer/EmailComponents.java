package com.emailslicer;

/**
 * Immutable record holding the parsed components of an email address.
 *
 * @param username the part before the '@' symbol
 * @param domain   the part after the '@' symbol
 */
public record EmailComponents(String username, String domain) {}
