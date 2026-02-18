package com.emailslicer;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Main entry point for the Email Slicer CLI application.
 * This simple tool parses email addresses into username and domain components.
 *
 * Input Acquisition Strategy: Hybrid Mode
 * - When args.length == 1: Treats args[0] as the email input (non-interactive)
 * - When args.length == 0: Prompts the user interactively via System.in
 * - When args.length > 1: Prints usage message and exits with error code
 */
public class EmailSlicer {

    /**
     * Main method - entry point for the application.
     *
     * Supports two modes of operation:
     * 1. Interactive mode: Prompts user for email via stdin (no arguments)
     * 2. Command-line mode: Accepts email as first argument (single argument)
     *
     * @param args command-line arguments (optional email address)
     */
    public static void main(String[] args) {
        int exitCode = run(args, System.in, System.out);
        System.exit(exitCode);
    }

    /**
     * Runs the email slicer logic without calling System.exit().
     * This method is separated to enable testing without terminating the JVM.
     *
     * @param args command-line arguments
     * @param in input stream for reading user input
     * @param out output stream for writing results
     * @return exit code (0 for success, 1 for error)
     */
    static int run(String[] args, InputStream in, PrintStream out) {
        String emailInput;

        // Hybrid mode: Check for command-line argument or use interactive input
        if (args.length == 0) {
            // Interactive mode: prompt user via Scanner
            Scanner scanner = new Scanner(in);
            out.print("Please enter your Email Id: ");
            emailInput = scanner.nextLine();
            // Note: Scanner is not closed to avoid closing the provided InputStream
            // This is acceptable for this simple single-use CLI tool
        } else if (args.length == 1) {
            // Command-line argument mode: use provided email
            emailInput = args[0];
        } else {
            // Too many arguments: show usage and return error code
            out.println("Usage: java -jar emailslicer-cli-1.0.0.jar [email]");
            out.println("  With no arguments: interactive mode (prompts for email)");
            out.println("  With one argument: parses the provided email");
            return 1;
        }

        // Create parser and process the email
        EmailParser parser = new EmailParser();
        try {
            EmailResult result = parser.parse(emailInput);

            // Output format matches Python's print("Your username is: ", username)
            // Python's comma-separated print adds a space, so we use ": " in Java
            out.println("Your username is:  " + result.username());
            out.println("Your domain is:  " + result.domain());

            return 0;
        } catch (EmailValidationException e) {
            // Print error message without stack trace, matching Python's simple error output
            out.println("Please enter a valid Email Id.");
            return 1;
        }
    }
}
