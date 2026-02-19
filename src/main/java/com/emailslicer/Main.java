package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user to enter an email address via standard input,
 * delegates parsing to {@link EmailSlicer}, and prints the username
 * and domain to standard output. Exits with code 1 on invalid input.</p>
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your email address: ");

        if (!scanner.hasNextLine()) {
            System.err.println("No input provided.");
            System.exit(1);
        }

        String input = scanner.nextLine();
        EmailSlicer slicer = new EmailSlicer();
        Optional<EmailSlicer.EmailParts> result = slicer.slice(input);

        if (result.isEmpty()) {
            System.err.println("Invalid email address.");
            System.exit(1);
        }

        EmailSlicer.EmailParts parts = result.get();
        System.out.println("Your username is: " + parts.getUsername());
        System.out.println("Your domain is: " + parts.getDomain());
    }
}
