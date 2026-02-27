package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the result or an error message.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter an email address:");

        try (var scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            var slicer = new EmailSlicer();
            EmailParts parts = slicer.parse(input);

            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } catch (InvalidEmailException e) {
            System.out.println("Invalid email address.");
        }
    }
}
