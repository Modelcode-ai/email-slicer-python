package com.emailslicer;

import java.util.Scanner;

/**
 * Main entry point for the Email Slicer application.
 * Provides an interactive CLI for parsing email addresses into username and domain components.
 */
public class Main {
  /**
   * Main method - entry point for the application.
   * Reads an email address from standard input, parses it using EmailSlicerService,
   * and displays the username and domain components or an error message.
   *
   * @param args command line arguments (not currently used)
   */
  public static void main(String[] args) {
    // Create service instance
    EmailSlicerService service = new EmailSlicerService();

    // Use try-with-resources for proper resource management
    try (Scanner scanner = new Scanner(System.in)) {
      // Display prompt (modernized from Python's "Please enter your Email Id:")
      System.out.print("Enter your email address: ");

      // Read user input
      String input = scanner.nextLine();

      try {
        // Parse the email using the service
        EmailComponents components = service.parse(input);

        // Display successful result
        System.out.println("Username: " + components.username());
        System.out.println("Domain: " + components.domain());
      } catch (InvalidEmailException e) {
        // Display user-friendly error message
        System.out.println("Invalid email: " + e.getMessage());
      }
    }
  }
}
