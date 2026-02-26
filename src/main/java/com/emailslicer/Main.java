package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user to enter an email address, delegates parsing to {@link EmailSlicer},
 * and prints the username and domain components. For invalid input, prints an error message
 * to {@code stderr} and exits with status code 1.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.print("Enter your email: ");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            Optional<EmailParts> result = EmailSlicer.parse(input);

            if (result.isPresent()) {
                EmailParts parts = result.get();
                System.out.println("Your username is: " + parts.username());
                System.out.println("Your domain is: " + parts.domain());
            } else {
                System.err.println("Invalid email address.");
                System.exit(1);
            }
        }
    }
}
