package com.emailslicer;

/**
 * Value object holding the parsed username and domain from an email address.
 */
public record EmailParts(String username, String domain) {
}
