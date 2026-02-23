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
        // Handle null input
        if (rawInput == null) {
            return new EmailSliceResult(false, null, null, "Please enter a valid Email Id.");
        }

        // Trim whitespace (matching Python's strip() semantics)
        String email = rawInput.strip();

        // Check for presence of @ symbol
        int atIndex = email.indexOf("@");
        if (atIndex == -1) {
            return new EmailSliceResult(false, null, null, "Please enter a valid Email Id.");
        }

        // Extract username and domain using first @ as delimiter
        String username = email.substring(0, atIndex);
        String domain = email.substring(atIndex + 1);

        return new EmailSliceResult(true, username, domain, null);
    }
}
