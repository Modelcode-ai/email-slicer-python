package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool. Handles all user I/O and
 * delegates parsing logic to {@link EmailSlicer}.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();

        try {
            EmailSlicer.EmailParts parts = slicer.slice(email);
            // Python's print("Your username is: ", username) produces two spaces
            // between the colon and the value because print inserts sep=' ' between
            // the trailing-space string literal and the variable.
            System.out.println("Your username is:  " + parts.username());
            System.out.println("Your domain is:  " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Email Id.");
            System.exit(1);
        }
    }
}
