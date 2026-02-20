package com.emailslicer;

/**
 * Immutable value type representing the parsed components of an email address.
 *
 * <p>Both {@code username} and {@code domain} may be empty strings — for example,
 * input {@code "@domain"} yields an empty username, and input {@code "user@"} yields
 * an empty domain. No additional normalization or validation is applied beyond what
 * {@link EmailSlicer} performs.</p>
 */
public record EmailParts(String username, String domain) {
}
