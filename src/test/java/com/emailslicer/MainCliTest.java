package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link Main} CLI entry point.
 *
 * <p>These tests simulate stdin input and capture stdout output to verify
 * that the CLI behavior matches the original Python email slicer script.</p>
 */
class MainCliTest {

    private ByteArrayOutputStream outputCapture;
    private PrintStream printStream;

    @BeforeEach
    void setUp() {
        outputCapture = new ByteArrayOutputStream();
        printStream = new PrintStream(outputCapture, true, StandardCharsets.UTF_8);
    }

    @AfterEach
    void tearDown() {
        printStream.close();
    }

    /**
     * Runs Main.run() with the given simulated input and returns the captured output.
     */
    private String runWithInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)
        );
        Main.run(inputStream, printStream);
        return outputCapture.toString(StandardCharsets.UTF_8);
    }

    /**
     * Runs Main.run() with the given simulated input and returns the exit code.
     */
    private int runAndGetExitCode(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)
        );
        return Main.run(inputStream, printStream);
    }

    // ---- Happy path CLI behavior ----

    @Test
    @DisplayName("Valid email prints prompt, username, and domain lines")
    void validEmailOutputFormat() {
        String output = runWithInput("avimax37@gmail.com\n");

        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the prompt");
        assertTrue(output.contains("Your username is: avimax37"),
                "Output should contain the username line");
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Output should contain the domain line");
    }

    @Test
    @DisplayName("Valid email returns exit code 0")
    void validEmailExitCodeZero() {
        int exitCode = runAndGetExitCode("avimax37@gmail.com\n");
        assertEquals(0, exitCode);
    }

    @Test
    @DisplayName("Output lines appear in correct order: prompt, username, domain")
    void validEmailOutputOrder() {
        String output = runWithInput("test@example.org\n");

        int promptPos = output.indexOf("Please enter your Email Id:");
        int usernamePos = output.indexOf("Your username is: test");
        int domainPos = output.indexOf("Your domain is: example.org");

        assertTrue(promptPos >= 0, "Prompt should be present");
        assertTrue(usernamePos > promptPos, "Username line should appear after prompt");
        assertTrue(domainPos > usernamePos, "Domain line should appear after username line");
    }

    // ---- Invalid input CLI behavior ----

    @Test
    @DisplayName("Invalid email (no '@') prints error message")
    void invalidEmailPrintsError() {
        String output = runWithInput("plainaddress\n");

        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain the error message");
    }

    @Test
    @DisplayName("Invalid email does not print username or domain lines")
    void invalidEmailNoUsernameOrDomain() {
        String output = runWithInput("plainaddress\n");

        assertFalse(output.contains("Your username is:"),
                "Output should NOT contain username line for invalid input");
        assertFalse(output.contains("Your domain is:"),
                "Output should NOT contain domain line for invalid input");
    }

    @Test
    @DisplayName("Invalid email returns exit code 1")
    void invalidEmailExitCodeOne() {
        int exitCode = runAndGetExitCode("plainaddress\n");
        assertEquals(1, exitCode);
    }

    @Test
    @DisplayName("Empty input prints error message")
    void emptyInputPrintsError() {
        String output = runWithInput("\n");

        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain the error message for empty input");
        assertFalse(output.contains("Your username is:"),
                "Output should NOT contain username line for empty input");
        assertFalse(output.contains("Your domain is:"),
                "Output should NOT contain domain line for empty input");
    }

    // ---- Whitespace handling ----

    @Test
    @DisplayName("Email with leading/trailing spaces is trimmed before parsing")
    void whitespaceAroundEmailIsTrimmed() {
        String output = runWithInput("  avimax37@gmail.com  \n");

        assertTrue(output.contains("Your username is: avimax37"),
                "Username should match trimmed email");
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Domain should match trimmed email");
    }

    // ---- Boundary '@' positions ----

    @Test
    @DisplayName("'@' at beginning: @domain.com is treated as valid")
    void atBeginningTreatedAsValid() {
        String output = runWithInput("@domain.com\n");

        assertTrue(output.contains("Your username is: "),
                "Output should contain username line");
        assertTrue(output.contains("Your domain is: domain.com"),
                "Output should contain domain 'domain.com'");
        assertFalse(output.contains("Please enter a valid Email Id."),
                "Output should NOT contain error message");
    }

    @Test
    @DisplayName("'@' at end: user@ is treated as valid")
    void atEndTreatedAsValid() {
        String output = runWithInput("user@\n");

        assertTrue(output.contains("Your username is: user"),
                "Output should contain username 'user'");
        assertTrue(output.contains("Your domain is: "),
                "Output should contain domain line");
        assertFalse(output.contains("Please enter a valid Email Id."),
                "Output should NOT contain error message");
    }

    @Test
    @DisplayName("'@' at beginning returns exit code 0")
    void atBeginningExitCodeZero() {
        int exitCode = runAndGetExitCode("@domain.com\n");
        assertEquals(0, exitCode, "'@domain.com' should be treated as valid (exit code 0)");
    }

    @Test
    @DisplayName("'@' at end returns exit code 0")
    void atEndExitCodeZero() {
        int exitCode = runAndGetExitCode("user@\n");
        assertEquals(0, exitCode, "'user@' should be treated as valid (exit code 0)");
    }

    // ---- Exit behavior ----

    @Test
    @DisplayName("Application processes single input and terminates (no loop)")
    void singleInputProcessed() {
        // The run method should process one line and return without blocking
        String output = runWithInput("avimax37@gmail.com\n");

        // If run() returns, it means there's no infinite loop
        assertNotNull(output, "Output should not be null after processing");
        assertTrue(output.contains("Your username is:"),
                "Single input should produce output");
    }
}
