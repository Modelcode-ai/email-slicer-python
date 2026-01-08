package com.emailslicer;

/**
 * Record representing parsed email components.
 *
 * @param username the username part of the email (before the @ symbol)
 * @param domain the domain part of the email (after the @ symbol)
 */
public record EmailParts(String username, String domain) {
}
