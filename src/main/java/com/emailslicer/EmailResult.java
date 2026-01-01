package com.emailslicer;

/**
 * Represents the result of parsing an email address into its
 * constituent parts.
 *
 * <p>This immutable record encapsulates the username (local part
 * before the '@' symbol) and the domain (part after the '@' symbol)
 * extracted from an email address.</p>
 *
 * <p>Example: For the email "user@example.com":</p>
 * <ul>
 *   <li>username: "user"</li>
 *   <li>domain: "example.com"</li>
 * </ul>
 *
 * <p>This record is a pure data carrier with no validation logic.
 * Validation is performed by {@link EmailSlicer} before constructing
 * an instance of this record.</p>
 *
 * @param username the local part of the email address (before '@')
 * @param domain the domain part of the email address (after '@')
 *
 * @since 1.0.0
 */
public record EmailResult(String username, String domain) {
}
