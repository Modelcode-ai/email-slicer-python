package com.emailslicer;

/**
 * Centralized user-facing message constants.
 * All text displayed to the user should be defined here.
 */
public final class Messages {

    private Messages() {
        // Prevent instantiation
    }

    /**
     * Prompt displayed to the user when requesting email input.
     */
    public static final String PROMPT_EMAIL = "Please enter your Email Id:";

    /**
     * Error message displayed when the input is not a valid email address.
     */
    public static final String INVALID_EMAIL = "Please enter a valid Email Id.";

    /**
     * Prefix for the username output line.
     */
    public static final String USERNAME_PREFIX = "Your username is: ";

    /**
     * Prefix for the domain output line.
     */
    public static final String DOMAIN_PREFIX = "Your domain is: ";
}
