package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Reads an email address from standard input, parses it into username and domain
 * using {@link EmailSlicer}, and prints the results to standard output.
 * This mirrors the behavior of the original Python {@code emailSlicer.py} script.
 */
public class Main {

    private static final String PROMPT_MESSAGE = "Please enter your Email Id:";
    private static final String INVALID_MESSAGE = "Invalid email id";
    private static final String USERNAME_LABEL = "Your username is: ";
    private static final String DOMAIN_LABEL = "Your domain is: ";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(PROMPT_MESSAGE);

            if (!scanner.hasNextLine()) {
                System.out.println(INVALID_MESSAGE);
                return;
            }

            String input = scanner.nextLine();
            EmailParts parts = EmailSlicer.slice(input);

            if (parts == null) {
                System.out.println(INVALID_MESSAGE);
            } else {
                System.out.println(USERNAME_LABEL + parts.username());
                System.out.println(DOMAIN_LABEL + parts.domain());
            }
        }
    }
}
