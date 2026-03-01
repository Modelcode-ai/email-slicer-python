package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Prompts the user for an email address, slices it into username and domain,
 * and prints the results. Mirrors the exact behavior and output format of the
 * original Python emailSlicer.py script.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Matches Python: print("Please enter your Email Id:")
        System.out.println("Please enter your Email Id:");
        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        try {
            EmailSlicer.Result result = slicer.slice(input);

            // Matches Python: print("Your username is: ", username)
            // Python's print with comma-separated args inserts a space separator,
            // producing "Your username is:  avimax37" (two spaces after colon)
            System.out.println("Your username is:  " + result.getUsername());
            System.out.println("Your domain is:  " + result.getDomain());
        } catch (IllegalArgumentException ex) {
            // Matches Python: print("Please enter a valid Email Id.")
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
