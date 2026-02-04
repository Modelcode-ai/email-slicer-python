package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * Handles user interaction via console I/O and delegates parsing to EmailSlicer.
 */
public class Main {

    private static final String PROMPT = "Please enter your Email Id:";
    private static final String INVALID_MESSAGE = "Please enter a valid Email Id.";
    private static final String USERNAME_PREFIX = "Your username is:  ";
    private static final String DOMAIN_PREFIX = "Your domain is:  ";

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println(PROMPT);
            String input = scanner.nextLine();

            try {
                EmailSlicer.EmailParts parts = EmailSlicer.parse(input);
                System.out.println(USERNAME_PREFIX + parts.getUsername());
                System.out.println(DOMAIN_PREFIX + parts.getDomain());
            } catch (IllegalArgumentException e) {
                System.out.println(INVALID_MESSAGE);
            }
        }
    }
}
