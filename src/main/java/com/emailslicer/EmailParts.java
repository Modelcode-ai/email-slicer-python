package com.emailslicer;

/**
 * Immutable container for parsed email components.
 *
 * @param username the part of the email before the @ symbol
 * @param domain the part of the email after the @ symbol
 */
public record EmailParts(String username, String domain) {
}
