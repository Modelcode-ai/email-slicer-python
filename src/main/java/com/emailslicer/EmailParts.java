package com.emailslicer;

/**
 * Immutable value object representing the result of slicing
 * an email address.
 * Contains the username (local part) and domain extracted
 * from an email address.
 *
 * @param username the username (local part) of the email address
 * @param domain the domain part of the email address
 */
public record EmailParts(String username, String domain) {
}
