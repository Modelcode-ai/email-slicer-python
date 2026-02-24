package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Reads an email address from standard input, delegates parsing to
 * {@link EmailSlicer}, and prints the extracted username and domain.
 * Exits with code {@code 0} on success or {@code 1} on invalid input.
 */
public final class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextLine()) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }

            String input = scanner.nextLine();
            EmailSlicer slicer = new EmailSlicer();

            try {
                EmailSlicer.Result result = slicer.parse(input);
                System.out.println("Your username is: " + result.getUsername());
                System.out.println("Your domain is: " + result.getDomain());
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email Id.");
                System.exit(1);
            }
        }
    }
}
