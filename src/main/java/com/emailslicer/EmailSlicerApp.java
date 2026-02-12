package com.emailslicer;

import java.util.Scanner;

/**
 * Main entry point for the Email Slicer application.
 *
 * This class handles console I/O, delegating all email parsing logic
 * to the EmailSlicer class.
 */
public class EmailSlicerApp {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter your Email Id:");
            String email = scanner.nextLine();

            EmailSlicer slicer = new EmailSlicer();
            try {
                EmailParts parts = slicer.slice(email);
                // Note: Two spaces after colon to match Python's comma-separated print behavior
                System.out.println("Your username is:  " + parts.username());
                System.out.println("Your domain is:  " + parts.domain());
            } catch (IllegalArgumentException e) {
                System.out.println("Please enter a valid Email Id.");
            }
        }
    }
}
