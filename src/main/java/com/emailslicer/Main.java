package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 * <p>
 * Reads a single email address from standard input, delegates parsing
 * to {@link EmailSlicer}, and prints the username and domain to stdout.
 * On invalid input, prints an error message to stderr and exits with code 1.
 * <p>
 * The prompt and output format match the original Python script:
 * <pre>
 * Please enter your Email Id:
 * Your username is:  avimax37
 * Your domain is:  gmail.com
 * </pre>
 * Note: Two spaces before the value match Python's {@code print("label: ", value)}
 * separator behavior.
 */
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your Email Id:");

        String input = scanner.nextLine();
        EmailSlicer slicer = new EmailSlicer();

        try {
            EmailSliceResult result = slicer.slice(input);
            System.out.println("Your username is:  " + result.username());
            System.out.println("Your domain is:  " + result.domain());
        } catch (IllegalArgumentException ex) {
            System.err.println("Invalid email address.");
            System.exit(1);
        }
    }
}
