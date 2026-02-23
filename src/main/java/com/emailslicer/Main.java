package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, parses it into username and domain
 * components using {@link EmailSlicer}, and prints the results. Mirrors the
 * behavior of the original Python {@code emailSlicer.py} script.
 */
public class Main {

    /**
     * Application entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine();
            EmailSlicer slicer = new EmailSlicer();

            try {
                EmailParts parts = slicer.parse(email);
                // Python's print("Your username is: ", username) produces two spaces
                // after the colon due to comma-separated print argument behavior.
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }
        }
    }
}
