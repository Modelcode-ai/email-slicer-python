package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Replicates the interaction pattern of the original Python script:
 * <ol>
 *   <li>Prints {@code "Please enter your Email Id:"} (with trailing newline, matching Python's {@code print()})</li>
 *   <li>Reads a line from stdin</li>
 *   <li>Delegates parsing to {@link EmailSlicer}</li>
 *   <li>Prints username and domain, or an error message for invalid input</li>
 * </ol>
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your Email Id:");

        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        try {
            EmailSlicer.EmailParts parts = slicer.slice(input);
            // Python's print("Your username is: ", username) produces a space between
            // the comma-separated args (sep=" "), resulting in double space after colon.
            System.out.println("Your username is:  " + parts.username());
            System.out.println("Your domain is:  " + parts.domain());
        } catch (IllegalArgumentException ex) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
