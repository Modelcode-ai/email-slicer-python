package com.emailslicer;

import java.util.Scanner;

/**
 * Main entry point for the Email Slicer application.
 * Handles console I/O and delegates email parsing to EmailParser.
 */
public class EmailSlicer {

    // Exact prompts and messages matching Python output format
    private static final String PROMPT = "Please enter your Email Id:";
    // Note: Python's print("Your username is: ", username) adds a space between arguments,
    // resulting in two spaces after the colon. We match this exact behavior for parity.
    private static final String USERNAME_PREFIX = "Your username is:  ";
    private static final String DOMAIN_PREFIX = "Your domain is:  ";
    private static final String INVALID_MESSAGE = "Please enter a valid Email Id.";

    /**
     * Main method - entry point for the CLI application.
     * Reads email from stdin, parses it, and outputs results.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Print prompt
        System.out.println(PROMPT);

        // Read input using Scanner with try-with-resources for proper cleanup
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            // Delegate parsing to EmailParser
            EmailParser.Result result = EmailParser.parse(input);

            // Output based on validation result
            if (result.isValid()) {
                System.out.println(USERNAME_PREFIX + result.getUsername());
                System.out.println(DOMAIN_PREFIX + result.getDomain());
            } else {
                System.out.println(INVALID_MESSAGE);
            }
        }
    }
}
