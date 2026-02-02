package com.emailslicer;

import java.util.Scanner;

/**
 * Command-line interface for the Email Slicer tool.
 * Provides interactive input/output for parsing email addresses.
 */
public final class EmailSlicerCLI {

    private static final String PROMPT_MESSAGE = "Please enter your Email Id:";
    private static final String USERNAME_OUTPUT = "Your username is: ";
    private static final String DOMAIN_OUTPUT = "Your domain is: ";

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private EmailSlicerCLI() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    /**
     * Main entry point for the Email Slicer CLI application.
     * Prompts the user for an email address, parses it, and displays the results.
     * Exits with status code 1 if the input is invalid.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(PROMPT_MESSAGE);
            String input = scanner.nextLine();
            try {
                EmailSlicer.Result result = EmailSlicer.slice(input);
                System.out.println(USERNAME_OUTPUT + result.getUsername());
                System.out.println(DOMAIN_OUTPUT + result.getDomain());
            } catch (IllegalArgumentException ex) {
                System.out.println(ex.getMessage());
                System.exit(1);
            }
        }
    }
}
