package emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 * <p>
 * Reads an email address from standard input, delegates parsing to {@link EmailSlicer},
 * and prints the username and domain to standard output. On invalid input (no {@code @}),
 * prints an error message to standard output matching the original Python script behavior.
 * <p>
 * All output is directed to {@code System.out} for parity with the Python implementation
 * (which uses {@code print()} for all messages).
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Please enter your Email Id:");

        Scanner scanner = new Scanner(System.in);
        String email = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();

        try {
            EmailParts parts = slicer.slice(email);
            System.out.println("Your username is: " + parts.username());
            System.out.println("Your domain is: " + parts.domain());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
