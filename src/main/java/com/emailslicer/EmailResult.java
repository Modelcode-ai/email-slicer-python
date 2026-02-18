package com.emailslicer;

/**
 * Immutable data carrier for parsed email components.
 * This record holds the username (local part before @) and domain (part after @)
 * extracted from an email address.
 *
 * @param username the local part of the email address (before the @ symbol)
 * @param domain   the domain part of the email address (after the @ symbol)
 */
public record EmailResult(String username, String domain) {
}
