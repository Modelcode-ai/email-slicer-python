package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, parses it into username and domain
 * parts, and prints the result. Exits with code 1 if the input is invalid.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Messages.PROMPT);

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            EmailParts parts = EmailSlicer.parse(email);
            System.out.println(Messages.USERNAME_PREFIX + " " + parts.username());
            System.out.println(Messages.DOMAIN_PREFIX + " " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
