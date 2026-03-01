package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Prompts the user for an email address, delegates parsing to {@link EmailSlicer},
 * and prints the username and domain. Exits with code 0 on success or code 1 on
 * invalid input.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailSlicer slicer = new EmailSlicer();
            EmailParts parts = slicer.slice(input);

            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } catch (InvalidEmailException e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }
}
