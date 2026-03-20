package com.emailslicer;

/**
 * Immutable record holding the result of slicing an email address
 * into its username and domain components.
 *
 * @param username the part before the '@' symbol
 * @param domain   the part after the '@' symbol
 */
public record EmailSlicerResult(String username, String domain) {
}
