package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * Reads a single email address from stdin, parses it, and prints
 * the username and domain to stdout.
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextLine()) {
                System.err.println("Please enter a valid Email Id.");
                System.exit(1);
            }

            String email = scanner.nextLine().strip();

            EmailSlicer.EmailParts parts = EmailSlicer.parse(email);
            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }
}
