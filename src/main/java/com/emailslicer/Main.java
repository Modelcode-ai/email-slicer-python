package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Reads an email address from stdin, delegates parsing to
 * {@link EmailSlicer#parse(String)}, and prints the result to stdout.</p>
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            EmailSlicer.ParsedEmail parsed = EmailSlicer.parse(input);
            // Two spaces between colon and value to match Python's print() sep behavior:
            // print("Your username is: ", username) → "Your username is:  avimax37"
            System.out.println("Your username is:  " + parsed.username());
            System.out.println("Your domain is:  " + parsed.domain());
        } catch (IllegalArgumentException e) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
