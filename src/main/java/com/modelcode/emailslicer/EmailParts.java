package com.modelcode.emailslicer;

/**
 * Immutable data structure representing the parsed components
 * of an email address.
 *
 * <p>This record holds the username (local part before the '@'
 * symbol) and the domain (part after the '@' symbol) extracted
 * from a valid email address.
 *
 * <p>Example: For "user@example.com", username is "user" and
 * domain is "example.com".
 *
 * @param username the local part of the email address before
 *                 the '@' symbol
 * @param domain the domain part of the email address after
 *               the '@' symbol
 */
public record EmailParts(String username, String domain) {
}
