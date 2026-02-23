package com.emailslicer;

/**
 * Service class for email parsing and validation logic.
 * Separates business logic from CLI interaction.
 */
public class EmailSlicerService {

    /**
     * Parses an email address into username and domain components.
     *
     * @param rawInput the raw input string (may include whitespace)
     * @return EmailSliceResult containing parsing results
     */
    public EmailSliceResult slice(String rawInput) {
        // Stub implementation - to be completed in subsequent tasks
        return new EmailSliceResult(false, "", "", "Not implemented");
    }
}
