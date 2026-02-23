package com.emailslicer;

/**
 * Immutable record representing the result of email parsing.
 *
 * @param valid        true if the email was valid (contains @)
 * @param username     the username portion (before @)
 * @param domain       the domain portion (after @)
 * @param errorMessage error message if invalid, null otherwise
 */
public record EmailSliceResult(boolean valid, String username, String domain, String errorMessage) {
}
