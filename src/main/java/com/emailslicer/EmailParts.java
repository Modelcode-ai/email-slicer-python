package com.emailslicer;

/**
 * Immutable value object holding the parsed username and domain
 * components of an email address.
 */
public record EmailParts(String username, String domain) { }
