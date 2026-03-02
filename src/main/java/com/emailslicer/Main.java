package com.emailslicer;

import java.util.Scanner;
import java.util.logging.Logger;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, delegates parsing to {@link EmailSlicer},
 * and prints the username and domain — or an error message for invalid input.
 * <p>
 * Output format matches the original Python script exactly, including the
 * double-space after "is:" caused by Python's {@code print()} separator.
 */
public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        LOGGER.info("Email Slicer application started");
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailSlicer slicer = new EmailSlicer();
            try {
                EmailParts parts = slicer.parse(input);
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }
        }
    }
}
