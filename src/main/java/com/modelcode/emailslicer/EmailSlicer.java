package com.modelcode.emailslicer;

import java.util.Scanner;

/**
 * Main CLI entry point for the Email Slicer application.
 * Provides a console-based interface for parsing email addresses.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        // Use try-with-resources to ensure Scanner is properly closed
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt the user for email input (matching Python version)
            System.out.println("Please enter your Email Id:");

            // Read one line of input from stdin
            String input = scanner.nextLine();

            // Instantiate EmailParser and attempt to parse the input
            EmailParser parser = new EmailParser();

            try {
                EmailResult result = parser.parse(input);

                // On success, print the username and domain
                // Python's print("text: ", value) adds a space between arguments
                // So we use "text:  " with explicit double space to match
                System.out.println("Your username is:  " + result.username());
                System.out.println("Your domain is:  " + result.domain());

            } catch (IllegalArgumentException e) {
                // On validation failure, print user-friendly error message
                // Do not expose technical details or stack traces to the user
                System.out.println("Please enter a valid Email Id.");
            }
        }

        // Exit with status code 0 (success)
        // No special error codes are needed for this simple tool
    }
}
