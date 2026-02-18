package com.emailslicer;

import java.util.Scanner;

/**
 * Command-line interface entry point for the Email Slicer application.
 * <p>
 * This class provides an interactive console interface that:
 * - Prompts the user to enter an email address
 * - Parses the email using EmailParser
 * - Displays the username and domain components, or an error message for invalid input
 * <p>
 * The output format matches the original Python script exactly, including the two spaces
 * after the colon in success messages (due to Python's print() comma separator behavior).
 */
public class EmailSlicer {

    /**
     * Main entry point for the Email Slicer CLI application.
     * <p>
     * Behavior:
     * 1. Prompts user with "Please enter your Email Id:"
     * 2. Reads one line of input
     * 3. Parses the input using EmailParser.parse()
     * 4. If valid: prints username and domain (with two spaces after colon)
     * 5. If invalid: prints error message "Please enter a valid Email Id."
     * 6. Exits after processing one email
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Print prompt (matches Python: newline after prompt)
            System.out.println("Please enter your Email Id:");

            // Read input line
            String input = scanner.nextLine();

            // Parse the email
            EmailParts parts = EmailParser.parse(input);

            // Check result and display output
            if (parts == null) {
                // Invalid input: display error message
                System.out.println("Please enter a valid Email Id.");
            } else {
                // Valid input: display username and domain
                // Note: Two spaces after colon to match Python's print("text: ", variable) behavior
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            }
        }
    }
}
