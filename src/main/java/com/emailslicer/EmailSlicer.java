package com.emailslicer;

import java.util.Optional;

/**
 * Pure parsing logic for slicing an email address into username and domain.
 * This class contains no I/O — it operates on strings only.
 *
 * <p>The current implementation mirrors the Python original's behavior:
 * it splits on the first '@' character found in the input. More rigorous
 * validation (e.g., regex-based) is planned for a future milestone.</p>
 */
public final class EmailSlicer {

    private EmailSlicer() {
        // Prevent instantiation
    }

    /**
     * Slices an email address into its username and domain components.
     *
     * <p>The input is stripped of leading/trailing whitespace before processing.
     * The split is performed on the first occurrence of '@', matching the
     * behavior of the original Python implementation.</p>
     *
     * @param email the email address to slice
     * @return an {@link Optional} containing the result if the email contains '@',
     *         or {@link Optional#empty()} if the input is null, blank, or lacks '@'
     */
    public static Optional<EmailSlicerResult> slice(String email) {
        if (email == null || email.isBlank()) {
            return Optional.empty();
        }

        String trimmed = email.strip();
        int atIndex = trimmed.indexOf('@');

        if (atIndex == -1) {
            return Optional.empty();
        }

        String username = trimmed.substring(0, atIndex);
        String domain = trimmed.substring(atIndex + 1);

        return Optional.of(new EmailSlicerResult(username, domain));
    }
}
