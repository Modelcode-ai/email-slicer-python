package com.emailslicer;

import java.util.Scanner;

/**
 * Command-line interface for the Email Slicer application.
 * Prompts the user to enter an email address, validates it, and displays
 * the username and domain components.
 * This class serves as the main entry point for the application.
 */
public final class EmailSlicer {

    /**
     * Private constructor to prevent instantiation.
     * This is a utility class with only a main method.
     */
    private EmailSlicer() {
        throw new AssertionError("EmailSlicer cannot be instantiated");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     * Prompts the user for an email address, validates and parses it,
     * then displays the username and domain components.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Prompt user for email input
            System.out.println("Please enter your Email Id:");

            // Read and trim input
            final String input = scanner.nextLine().trim();

            try {
                // Parse the email using EmailParser
                final EmailParser.ParsedEmail result = EmailParser.parse(input);

                // Display the username and domain
                System.out.println("Your username is: " + result.username());
                System.out.println("Your domain is: " + result.domain());

            } catch (ValidationException e) {
                // Handle validation failures
                System.err.println("Invalid email address: " + e.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("An unexpected error occurred: "
                    + e.getMessage());
            System.exit(2);
        }
    }
}
