package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Supports two modes of operation:</p>
 * <ul>
 *     <li><strong>Non-interactive:</strong> Pass the email as a command-line argument,
 *         e.g. {@code java -jar email-slicer-java.jar user@example.com}</li>
 *     <li><strong>Interactive:</strong> Run without arguments to be prompted for input,
 *         e.g. {@code java -jar email-slicer-java.jar}</li>
 * </ul>
 *
 * <p>On valid input, prints the username and domain to {@code stdout}.
 * On invalid input, prints an error message to {@code stderr} and exits with code 1.</p>
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args optional; if provided, {@code args[0]} is used as the email address
     */
    public static void main(String[] args) {
        String email;

        if (args.length >= 1) {
            email = args[0];
        } else {
            System.out.print("Enter your email: ");
            try (Scanner scanner = new Scanner(System.in)) {
                email = scanner.nextLine();
            }
        }

        try {
            EmailParts parts = EmailSlicer.parse(email);
            System.out.println("Username: " + parts.username());
            System.out.println("Domain: " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
