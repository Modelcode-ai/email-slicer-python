package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Console application entry point for the Email Slicer tool.
 * Reads an email address from stdin, parses it into username and domain,
 * and prints the results to stdout.
 */
public class EmailSlicer {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String email = reader.readLine();

            if (email == null) {
                System.out.println("Please enter a valid Email Id.");
                return;
            }

            email = email.trim();

            EmailParser.ParsedEmail result = EmailParser.sliceEmail(email);

            if (result != null) {
                System.out.println("Your username is:  " + result.username());
                System.out.println("Your domain is:  " + result.domain());
            } else {
                System.out.println("Please enter a valid Email Id.");
            }
        } catch (IOException e) {
            System.out.println("Please enter a valid Email Id.");
        }
    }
}
