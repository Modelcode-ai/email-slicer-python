package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the EmailSlicer CLI application.
 * These tests verify end-to-end behavior including input reading, output formatting,
 * and error handling.
 *
 * Note: These tests do not test System.exit() behavior since that would terminate
 * the test JVM. In a production environment, consider refactoring to make exit codes
 * testable or use a Security Manager.
 */
class EmailSlicerCliTest {

    /**
     * Helper class to hold both output and exit code from CLI execution.
     */
    private static class CliResult {
        final String output;
        final int exitCode;

        CliResult(String output, int exitCode) {
            this.output = output;
            this.exitCode = exitCode;
        }
    }

    /**
     * Runs the CLI and captures output without terminating the JVM.
     *
     * @param input the simulated user input (for interactive mode, null for argument mode)
     * @param args command-line arguments
     * @return CliResult containing captured output and exit code
     */
    private CliResult runCliAndCaptureOutput(String input, String... args) {
        // Set up input simulation
        InputStream testIn = input != null
            ? new ByteArrayInputStream(input.getBytes())
            : new ByteArrayInputStream(new byte[0]);

        // Capture output
        ByteArrayOutputStream testOut = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(testOut);

        // Run the CLI logic without calling System.exit
        int exitCode = EmailSlicer.run(args, testIn, printStream);

        return new CliResult(testOut.toString(), exitCode);
    }

    // ========== Interactive Mode Tests ==========

    @Test
    void main_interactiveModeWithValidEmail_producesCorrectOutput() {
        // Use the example from the Python README
        CliResult result = runCliAndCaptureOutput("avimax37@gmail.com\n");

        // Verify successful exit code
        assertEquals(0, result.exitCode, "Should exit with code 0 for valid email");

        // Verify prompt is displayed
        assertTrue(result.output.contains("Please enter your Email Id:"),
                "Output should contain the input prompt");

        // Verify exact output format matching Python's spacing
        // Python's print("Your username is: ", username) adds two spaces total
        assertTrue(result.output.contains("Your username is:  avimax37"),
                "Output should contain username with exact spacing");
        assertTrue(result.output.contains("Your domain is:  gmail.com"),
                "Output should contain domain with exact spacing");
    }

    @Test
    void main_interactiveModeWithSimpleEmail_producesCorrectOutput() {
        CliResult result = runCliAndCaptureOutput("user@example.com\n");

        assertEquals(0, result.exitCode);
        assertTrue(result.output.contains("Your username is:  user"));
        assertTrue(result.output.contains("Your domain is:  example.com"));
    }

    @Test
    void main_interactiveModeWithWhitespace_trimsAndParses() {
        CliResult result = runCliAndCaptureOutput("  user@example.com  \n");

        assertEquals(0, result.exitCode);
        assertTrue(result.output.contains("Your username is:  user"));
        assertTrue(result.output.contains("Your domain is:  example.com"));
    }

    @Test
    void main_interactiveModeWithInvalidEmail_showsErrorMessage() {
        // Test email without @ symbol
        CliResult result = runCliAndCaptureOutput("invalidemail\n");

        // Verify error exit code
        assertEquals(1, result.exitCode, "Should exit with code 1 for invalid email");

        // Should show error message matching Python's output
        assertTrue(result.output.contains("Please enter a valid Email Id."),
                "Output should contain error message");

        // Should NOT contain stack trace
        assertFalse(result.output.contains("Exception"),
                "Output should not contain exception stack trace");
        assertFalse(result.output.contains("at com.emailslicer"),
                "Output should not contain stack trace lines");
    }

    @Test
    void main_interactiveModeWithEmptyUsername_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput("@example.com\n");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
        assertFalse(result.output.contains("Exception"));
    }

    @Test
    void main_interactiveModeWithEmptyDomain_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput("user@\n");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
        assertFalse(result.output.contains("Exception"));
    }

    @Test
    void main_interactiveModeWithMultipleAtSymbols_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput("user@@example.com\n");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
        assertFalse(result.output.contains("Exception"));
    }

    // ========== Command-Line Argument Mode Tests ==========

    @Test
    void main_commandLineArgumentWithValidEmail_producesCorrectOutput() {
        // Note: Input is null since we're using command-line argument mode
        CliResult result = runCliAndCaptureOutput(null, "test@domain.com");

        assertEquals(0, result.exitCode);

        // Should NOT display the prompt in argument mode
        assertFalse(result.output.contains("Please enter your Email Id:"),
                "Prompt should not appear when email is provided as argument");

        // Should display parsed results
        assertTrue(result.output.contains("Your username is:  test"));
        assertTrue(result.output.contains("Your domain is:  domain.com"));
    }

    @Test
    void main_commandLineArgumentWithComplexEmail_producesCorrectOutput() {
        CliResult result = runCliAndCaptureOutput(null, "user.name+tag@sub.domain.co");

        assertEquals(0, result.exitCode);
        assertTrue(result.output.contains("Your username is:  user.name+tag"));
        assertTrue(result.output.contains("Your domain is:  sub.domain.co"));
    }

    @Test
    void main_commandLineArgumentWithInvalidEmail_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput(null, "invalidemail");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
        assertFalse(result.output.contains("Exception"));
    }

    @Test
    void main_commandLineArgumentWithWhitespace_trimsAndParses() {
        CliResult result = runCliAndCaptureOutput(null, "  user@example.com  ");

        assertEquals(0, result.exitCode);
        assertTrue(result.output.contains("Your username is:  user"));
        assertTrue(result.output.contains("Your domain is:  example.com"));
    }

    // ========== Multiple Arguments Test ==========

    @Test
    void main_multipleArguments_showsUsageMessage() {
        CliResult result = runCliAndCaptureOutput(null, "email1@test.com", "email2@test.com");

        assertEquals(1, result.exitCode, "Should exit with code 1 for invalid arguments");

        // Should show usage information
        assertTrue(result.output.contains("Usage:") || result.output.contains("usage"),
                "Output should contain usage information");

        // Should NOT attempt to parse email
        assertFalse(result.output.contains("Your username is:"),
                "Should not parse email when multiple arguments provided");
    }

    // ========== Output Format Verification ==========

    @Test
    void main_outputFormat_exactlyMatchesPython() {
        CliResult result = runCliAndCaptureOutput("test@example.com\n");

        assertEquals(0, result.exitCode);

        // Verify exact line format with two spaces after colon
        // Python: print("Your username is: ", username) produces ":  " (two spaces)
        String[] lines = result.output.split("\n");

        boolean foundUsername = false;
        boolean foundDomain = false;

        for (String line : lines) {
            if (line.contains("Your username is:  test")) {
                foundUsername = true;
                // Verify exact spacing: colon followed by exactly two spaces
                assertTrue(line.matches(".*Your username is:  test.*"),
                        "Username line should have exactly two spaces after colon");
            }
            if (line.contains("Your domain is:  example.com")) {
                foundDomain = true;
                // Verify exact spacing: colon followed by exactly two spaces
                assertTrue(line.matches(".*Your domain is:  example\\.com.*"),
                        "Domain line should have exactly two spaces after colon");
            }
        }

        assertTrue(foundUsername, "Output should contain username line with correct format");
        assertTrue(foundDomain, "Output should contain domain line with correct format");
    }

    // ========== Edge Cases ==========

    @Test
    void main_emptyInputString_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput("\n");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
    }

    @Test
    void main_whitespaceOnlyInput_showsErrorMessage() {
        CliResult result = runCliAndCaptureOutput("   \n");

        assertEquals(1, result.exitCode);
        assertTrue(result.output.contains("Please enter a valid Email Id."));
    }
}
