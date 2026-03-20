package com.emailslicer;

import java.util.Scanner;

/**
 * Console entry point for the Email Slicer CLI tool.
 * <p>
 * Mirrors the Python original's user-facing behavior:
 * prompts for an email address, parses it, and prints the username and domain.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine();

            EmailSlicer.slice(email).ifPresentOrElse(
                    result -> {
                        System.out.println("Your username is:  " + result.username());
                        System.out.println("Your domain is:  " + result.domain());
                    },
                    () -> System.out.println("Please enter a valid Email Id.")
            );
        }
    }
}
