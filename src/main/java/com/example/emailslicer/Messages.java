package com.example.emailslicer;

/**
 * Centralized constants for all user-facing messages.
 * This class prevents instantiation and exposes all messages
 * as public static final strings.
 */
public final class Messages {
    /**
     * Prompt message displayed to the user requesting email input.
     */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /**
     * Label prefix for displaying the extracted username.
     * Note: Contains trailing space for proper formatting.
     */
    public static final String OUTPUT_USERNAME = "Your username is: ";

    /**
     * Label prefix for displaying the extracted domain.
     * Note: Contains trailing space for proper formatting.
     */
    public static final String OUTPUT_DOMAIN = "Your domain is: ";

    /**
     * Error message displayed when the email address is invalid.
     */
    public static final String ERROR_INVALID_EMAIL =
            "Please enter a valid Email Id.";

    /**
     * Private constructor to prevent instantiation of this constants class.
     */
    private Messages() {
        // Prevent instantiation
    }
}
