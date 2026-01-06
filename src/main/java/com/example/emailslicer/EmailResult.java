package com.example.emailslicer;

/**
 * Represents the result of parsing an email address.
 *
 * @param username the local-part of the email address (before the @ symbol)
 * @param domain the domain part of the email address (after the @ symbol)
 */
public record EmailResult(String username, String domain) {
}
