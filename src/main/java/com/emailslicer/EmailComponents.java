package com.emailslicer;

/**
 * Immutable record representing the parsed components of an email address.
 * Contains the username (local part) and domain portions.
 */
public record EmailComponents(String username, String domain) {
}
