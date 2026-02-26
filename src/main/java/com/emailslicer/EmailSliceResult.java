package com.emailslicer;

/**
 * Immutable data carrier for the result of slicing an email address
 * into its username and domain components.
 *
 * @param username the part of the email address before the '@' symbol
 * @param domain   the part of the email address after the '@' symbol
 */
public record EmailSliceResult(String username, String domain) {
}
