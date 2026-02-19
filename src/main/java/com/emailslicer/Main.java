package com.emailslicer;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Prompts the user for an email address, parses it using
 * {@link EmailSlicer#parse(String)}, and prints the username and domain
 * to standard output. On invalid input or EOF, prints an error message
 * to standard error and exits with code 1.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.print("Enter your email: ");

        String rawInput;
        try (Scanner scanner = new Scanner(System.in)) {
            rawInput = scanner.nextLine();
        } catch (NoSuchElementException e) {
            // EOF encountered before a line was read
            System.err.println("Invalid email address: ");
            System.exit(1);
            return; // unreachable, but satisfies compiler
        }

        try {
            EmailComponents result = EmailSlicer.parse(rawInput);
            System.out.println("Username: " + result.username());
            System.out.println("Domain: " + result.domain());
        } catch (InvalidEmailException e) {
            System.err.println("Invalid email address: " + rawInput);
            System.exit(1);
        }
    }
}
