package com.emailslicer;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Prompts the user for an email address, delegates parsing to
 * {@link EmailSlicer}, and prints the username and domain or an
 * error message. Exact output text follows the modernization
 * specification (single-space formatting, "Enter your Email: " prompt).</p>
 */
public final class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter your Email: ");
        System.out.flush();

        String input = reader.readLine();

        EmailSlicer slicer = new EmailSlicer();
        EmailSlicer.Result result = slicer.parse(input);

        if (result == null) {
            System.out.println("Please enter a valid Email Id.");
        } else {
            System.out.println("Your username is: " + result.getUsername());
            System.out.println("Your domain is: " + result.getDomain());
        }
    }
}
