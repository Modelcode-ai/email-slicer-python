package com.emailslicer;

/**
 * Holds the parsed result of slicing an email address into username and domain.
 *
 * @param username the part before the first {@code @} (may be empty, never null)
 * @param domain   the part after the first {@code @} (may be empty, never null)
 */
public record EmailSliceResult(String username, String domain) { }
