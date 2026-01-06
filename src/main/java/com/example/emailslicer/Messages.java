package com.example.emailslicer;

public final class Messages {

    private Messages() {
    }

    /** Prompt message displayed to request email input from user. */
    public static final String PROMPT = "Please enter your Email Id:";

    /** Error message displayed when email validation fails. */
    public static final String INVALID_EMAIL = "Please enter a valid Email Id.";

    /** Label prefix for displaying the extracted username. */
    public static final String USERNAME_OUTPUT = "Your username is: ";

    /** Label prefix for displaying the extracted domain. */
    public static final String DOMAIN_OUTPUT = "Your domain is: ";
}
