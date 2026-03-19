package com.emailslicer;

import java.util.Scanner;

/**
 * Console entry point for the Email Slicer application.
 * Reads an email address from standard input, parses it, and prints the username and domain.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String email = scanner.nextLine().strip();

            EmailSlicer.slice(email).ifPresentOrElse(
                    parts -> {
                        System.out.println("Your username is:  " + parts.username());
                        System.out.println("Your domain is:  " + parts.domain());
                    },
                    () -> {
                        System.out.println("Please enter a valid Email Id.");
                        System.exit(1);
                    }
            );
        }
    }
}
