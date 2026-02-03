package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * Console entry point for the Email Slicer application.
 * Migrated from Python emailSlicer.py to Java 17.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            EmailParser parser = new EmailParser();
            Optional<EmailParts> result = parser.parse(input);

            if (result.isPresent()) {
                EmailParts parts = result.get();
                // Note: Two spaces after colon matches Python's print("text: ", var) behavior
                System.out.println("Your username is:  " + parts.getUsername());
                System.out.println("Your domain is:  " + parts.getDomain());
            } else {
                System.out.println("Please enter a valid Email Id.");
            }
        }
    }
}
