package com.modelcode.emailslicer;

import com.modelcode.emailslicer.core.EmailComponents;
import com.modelcode.emailslicer.core.EmailSlicer;
import com.modelcode.emailslicer.core.InvalidEmailException;
import java.util.Scanner;

/**
 * Entry point for the Email Slicer CLI application.
 * This class handles user interaction via standard input/output and delegates
 * email parsing to the EmailSlicer core logic.
 */
public final class Main {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private Main() {
        throw new UnsupportedOperationException(
            "Utility class cannot be instantiated");
    }

    /**
     * Main entry point for the application.
     * Prompts the user for an email address, parses it, and displays the
     * username and domain components. On validation failure, prints an error
     * message to stderr and exits with code 1.
     *
     * @param args command line arguments (not used)
     */
    public static void main(final String[] args) {
        // Create scanner for reading user input
        final Scanner scanner = new Scanner(System.in);

        try {
            // Print prompt (preserving exact Python wording)
            System.out.print("Enter your Email Id: ");

            // Read one line of input
            final String emailInput = scanner.nextLine();

            // Parse the email using EmailSlicer
            final EmailSlicer slicer = new EmailSlicer();
            final EmailComponents components = slicer.slice(emailInput);

            // Print the parsed components (per specification format)
            System.out.println("Your username is: " + components.username());
            System.out.println("Your domain is: " + components.domain());

        } catch (InvalidEmailException e) {
            // Print error message to stderr
            System.err.println("Error: " + e.getMessage());
            // Exit with non-zero status code
            System.exit(1);
        } finally {
            // Close the scanner
            scanner.close();
        }
    }
}
