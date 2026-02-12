package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * Prompts the user for an email address and prints the parsed username and domain.
 */
public final class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();

            try {
                EmailSlicer.ParsedEmail parsed = EmailSlicer.parse(input);
                System.out.println("Your username is: " + parsed.getUsername());
                System.out.println("Your domain is: " + parsed.getDomain());
            } catch (IllegalArgumentException ex) {
                System.out.println("Please enter a valid Email Id.");
            }
        }
    }
}
