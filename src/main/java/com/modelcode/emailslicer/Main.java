package com.modelcode.emailslicer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * CLI entry point for the Email Slicer tool.
 * Reads a single email address from standard input, parses it, and prints
 * the username and domain to standard output.
 */
public final class Main {

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String line = reader.readLine();
            EmailParts parts = EmailSlicer.parse(line);

            System.out.println("Username: " + parts.getUsername());
            System.out.println("Domain: " + parts.getDomain());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid email address. Please provide a value like user@example.com.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Error reading input: " + e.getMessage());
            System.exit(1);
        }
    }
}
