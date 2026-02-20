package ai.modelcode.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer tool.
 *
 * <p>This class is a thin I/O wrapper that prompts the user for an email address,
 * delegates parsing to {@link EmailSlicer}, and prints the results. All output
 * messages are designed to match the original Python {@code emailSlicer.py} script
 * byte-for-byte.</p>
 */
public class Main {

    // Output format constants — match the original Python script's output exactly.
    // Python's print("Your username is: ", username) inserts a space between
    // comma-separated arguments, resulting in a double space after the colon.
    private static final String PROMPT = "Please enter your Email Id:";
    private static final String USERNAME_FORMAT = "Your username is:  %s";
    private static final String DOMAIN_FORMAT = "Your domain is:  %s";
    private static final String ERROR_MESSAGE = "Please enter a valid Email Id.";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(PROMPT);

        String input = scanner.nextLine();

        EmailSlicer slicer = new EmailSlicer();

        try {
            EmailComponents components = slicer.slice(input);
            System.out.println(String.format(USERNAME_FORMAT, components.username()));
            System.out.println(String.format(DOMAIN_FORMAT, components.domain()));
        } catch (IllegalArgumentException e) {
            System.out.println(ERROR_MESSAGE);
        }
    }
}
