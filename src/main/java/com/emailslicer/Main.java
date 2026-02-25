package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the result. Output strings match
 * the original Python script's behavior exactly, including the double
 * space produced by Python's comma-separated {@code print()} arguments.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email Id:");
        String email = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();
        try {
            EmailSlicer.Result result = slicer.slice(email);
            System.out.println("Your username is:  " + result.getUsername());
            System.out.println("Your domain is:  " + result.getDomain());
        } catch (IllegalArgumentException ex) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
