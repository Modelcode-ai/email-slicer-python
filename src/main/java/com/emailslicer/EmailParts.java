package com.emailslicer;

/**
 * Value type holding the parsed username and domain from an email address.
 */
public record EmailParts(String username, String domain) {
}
