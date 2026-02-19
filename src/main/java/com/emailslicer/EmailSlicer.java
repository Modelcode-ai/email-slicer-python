package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Prompts the user for an email address, validates it, parses it into
 * username and domain components, and prints the result. This is the Java 17
 * equivalent of the original Python {@code emailSlicer.py} script.
 */
public class EmailSlicer {

    /**
     * Main entry point. Reads an email address from standard input,
     * validates and parses it, then prints the result.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter your Email Id:");

            String email = scanner.nextLine().strip();

            if (email.isEmpty() || !EmailValidator.isValid(email)) {
                System.out.println("Please enter a valid Email Id.");
                return;
            }

            EmailParser.EmailParts parts = EmailParser.parse(email);
            System.out.printf("Your username is %s and your domain is %s%n",
                    parts.getUsername(),
                    parts.getDomain());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
