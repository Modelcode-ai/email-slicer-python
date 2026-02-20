package com.emailslicer;

import java.util.Scanner;

/**
 * CLI entry point for the Email Slicer application.
 *
 * <p>Reads one line from standard input, delegates to {@link EmailSlicer} for
 * parsing, and prints the result or an error message to standard output.</p>
 *
 * <h2>Output String Choice (Design Decision #1)</h2>
 * <p>The Java output uses single-space labels as prescribed by the migration spec,
 * which is an intentional minor deviation from the original Python script. The
 * Python script used comma-separated {@code print} arguments (e.g.,
 * {@code print("Your username is: ", username)}), which inserts an extra space
 * between the label and value. The Java equivalent uses string concatenation
 * with a single space, matching the spec's prescribed format:</p>
 * <pre>
 *   Your username is: &lt;username&gt;
 *   Your domain is: &lt;domain&gt;
 * </pre>
 * <p>The error message has also been updated from Python's
 * {@code "Please enter a valid Email Id."} to {@code "Invalid email address."}
 * as prescribed by the spec.</p>
 *
 * <h2>Exit Code Strategy (Design Decision #2)</h2>
 * <p>Direct {@link System#exit(int)} calls are used: {@code 0} on success,
 * {@code 1} on any failure. Automated tests cover only {@link EmailSlicer},
 * not {@code Main}, to avoid terminating the Surefire test-runner JVM.</p>
 */
public class Main {

    // Output string constants — single-space format per migration spec.
    // (Note: Python's print with comma args would produce a double-space here.)
    static final String PROMPT         = "Enter your email address: ";
    static final String USERNAME_LABEL = "Your username is: ";
    static final String DOMAIN_LABEL   = "Your domain is: ";
    static final String ERROR_MESSAGE  = "Invalid email address.";
    static final String NO_INPUT_MSG   = "No input provided.";

    public static void main(String[] args) {
        EmailSlicer slicer = new EmailSlicer();

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print(PROMPT);

            if (!scanner.hasNextLine()) {
                System.err.println(NO_INPUT_MSG);
                System.exit(1);
            }

            String input = scanner.nextLine();

            try {
                EmailParts parts = slicer.parse(input);
                System.out.println(USERNAME_LABEL + parts.username());
                System.out.println(DOMAIN_LABEL + parts.domain());
                System.exit(0);
            } catch (InvalidEmailException ex) {
                System.out.println(ERROR_MESSAGE);
                System.exit(1);
            }
        }
    }
}
