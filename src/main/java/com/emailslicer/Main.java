package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain or an
 * error message. This mirrors the behavior of the original Python
 * {@code emailSlicer.py} script.</p>
 */
public class Main {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your email address: ");
            String input = scanner.nextLine();

            ParsedEmail result = EmailSlicer.parse(input);
            System.out.println("Username: " + result.username());
            System.out.println("Domain: " + result.domain());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid email address. It must contain an '@' character.");
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            System.exit(1);
        }
    }
}
