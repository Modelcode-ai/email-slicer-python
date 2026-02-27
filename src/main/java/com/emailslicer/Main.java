package com.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer application.
 * Reads an email address from stdin, parses it, and prints the
 * username and domain to stdout.
 */
public class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String input = reader.readLine();
            EmailSlicer.Result result = EmailSlicer.parse(input);

            System.out.println("Username: " + result.getUsername());
            System.out.println("Domain: " + result.getDomain());
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(1);
        }
    }
}
