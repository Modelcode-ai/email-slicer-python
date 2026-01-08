package com.emailslicer;

/**
 * Utility class containing user-facing message string constants.
 */
public final class Messages {

    /** Prompt message asking user for email input. */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /** Prefix for displaying username output. */
    public static final String OUTPUT_USERNAME = "Your username is: ";

    /** Prefix for displaying domain output. */
    public static final String OUTPUT_DOMAIN = "Your domain is: ";

    /** Error message for invalid email input. */
    public static final String ERROR_INVALID_EMAIL =
            "Please enter a valid Email Id.";

    private Messages() {
        // Prevent instantiation
    }
}
