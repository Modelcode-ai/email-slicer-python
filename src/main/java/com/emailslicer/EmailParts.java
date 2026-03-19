package com.emailslicer;

/**
 * Immutable data carrier holding the parsed username and domain parts of an email address.
 */
public record EmailParts(String username, String domain) {
}
