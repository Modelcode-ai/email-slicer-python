package com.emailslicer;

/**
 * Represents the parsed components of an email address.
 * This record holds the username (part before @) and domain (part after @).
 */
public record EmailComponents(String username, String domain) {
}
