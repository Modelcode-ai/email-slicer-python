package com.emailslicer;

/**
 * Immutable data carrier for a parsed email address.
 *
 * @param username the local part of the email (before the {@code @})
 * @param domain   the domain part of the email (after the {@code @})
 */
public record EmailSliceResult(String username, String domain) {
}
