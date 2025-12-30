package com.emailslicer;

import com.emailslicer.validator.EmailValidator;
import com.emailslicer.validator.InvalidEmailException;

import java.util.Scanner;

/**
 * Command-line interface for the Email Slicer application.
 * Provides an interactive prompt for users to enter an email address
 * and displays the extracted username and domain.
 * This class serves as the main entry point for the application.
 */
public final class EmailSlicerCLI {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private EmailSlicerCLI() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     * Prompts the user to enter an email address, validates and slices it,
     * then displays the username and domain.
     * Exits with code 1 on validation failure or unexpected errors.
     *
     * @param args command-line arguments (ignored in v1.0)
     */
    public static void main(final String[] args) {
        // Print prompt to stdout (matching Python behavior)
        System.out.println("Please enter your Email Id:");

        // Use try-with-resources to ensure Scanner is properly closed
        try (Scanner scanner = new Scanner(System.in)) {
            // Read user input
            String input = scanner.nextLine();

            // Instantiate validator and slicer
            EmailValidator validator = new EmailValidator();
            EmailSlicer emailSlicer = new EmailSlicer(validator);

            try {
                // Slice the email address
                EmailParts parts = emailSlicer.slice(input);

                // Print results in format matching Python version
                // Note: Python's print() with comma adds a space,
                // producing "Your username is:  username"
                System.out.println("Your username is:  "
                        + parts.username());
                System.out.println("Your domain is:  " + parts.domain());

                // Exit with success code (implicit: System.exit(0))
            } catch (InvalidEmailException e) {
                // Print error message to stderr
                System.err.println("Invalid email: " + e.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            // Handle any unexpected errors
            System.err.println("An unexpected error occurred: "
                    + e.getMessage());
            System.exit(1);
        }
    }
}
