package com.emailslicer;

import java.util.Optional;
import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * Prompts the user for an email address, slices it into username and domain,
 * and prints the results.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Email Id:");
        String email = scanner.nextLine();

        Optional<EmailParts> result = EmailSlicer.slice(email);

        if (result.isPresent()) {
            EmailParts parts = result.get();
            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } else {
            System.out.println("Please enter a valid Email Id.");
            System.exit(1);
        }
    }
}
