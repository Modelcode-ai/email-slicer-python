package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain components
 * or a clear error message.</p>
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email: ");
        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        try {
            EmailParts parts = slicer.parse(input);
            System.out.println("Username: " + parts.username());
            System.out.println("Domain: " + parts.domain());
        } catch (IllegalArgumentException ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(1);
        }
    }
}
