package com.modelcode.emailslicer.core;

/**
 * Represents the components of an email address after parsing.
 * This is an immutable data structure that holds the username (local part)
 * and domain of an email address.
 *
 * <p>Example: For email "user@example.com", username is "user" and
 * domain is "example.com"</p>
 *
 * @param username the local part of the email address (before the '@')
 * @param domain the domain part of the email address (after the '@')
 */
public record EmailComponents(String username, String domain) {
}
