package com.example.emailslicer;

import java.util.Scanner;

/**
 * Interactive CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer#parse(String)}, and prints the extracted username
 * and domain — or a user-friendly error message if the input is invalid.</p>
 */
public final class EmailSlicerApp {

    private EmailSlicerApp() {
        // Entry-point class — not instantiable
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your email address: ");
            String input = scanner.nextLine();

            try {
                EmailSlicer.ParsedEmail parsed = EmailSlicer.parse(input);
                System.out.println("Your username is: " + parsed.getUsername());
                System.out.println("Your domain is: " + parsed.getDomain());
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid email: " + ex.getMessage());
            }
        }
    }
}
