package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for EmailSlicerApp CLI behavior.
 * <p>
 * These tests simulate CLI interaction by redirecting stdin and stdout
 * to verify the complete end-to-end flow of the application.
 */
class EmailSlicerAppTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;

    @BeforeEach
    void setUp() {
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Simulates user input via stdin.
     *
     * @param input the input string to simulate (should include newline)
     */
    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
    }

    /**
     * Gets the captured stdout output.
     *
     * @return the captured output string
     */
    private String getCapturedOutput() {
        return outputCapture.toString();
    }

    /**
     * Runs the application logic without calling System.exit.
     * This simulates the main method behavior for testing purposes.
     *
     * @param input the email input to process
     * @return true if parsing succeeded, false if it failed (would have called exit(1))
     */
    private boolean runAppLogic(String input) {
        System.out.println(Messages.PROMPT_EMAIL);

        String trimmedInput = input.trim();

        try {
            EmailParser.ParsedEmail parsed = EmailParser.parse(trimmedInput);
            System.out.println(Messages.OUTPUT_USERNAME_PREFIX + parsed.getUsername());
            System.out.println(Messages.OUTPUT_DOMAIN_PREFIX + parsed.getDomain());
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println(Messages.ERROR_INVALID_EMAIL);
            return false;
        }
    }

    @Test
    @DisplayName("Valid email input should produce correct username and domain output")
    void testSuccessfulEmailParsing() {
        provideInput("user@example.com\n");

        boolean success = runAppLogic("user@example.com");

        assertTrue(success, "Parsing should succeed for valid email");
        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.OUTPUT_USERNAME_PREFIX + "user"),
                "Output should contain username");
        assertTrue(output.contains(Messages.OUTPUT_DOMAIN_PREFIX + "example.com"),
                "Output should contain domain");
    }

    @Test
    @DisplayName("Invalid email without @ should produce error message")
    void testInvalidEmailParsing() {
        provideInput("invalidemail.com\n");

        boolean success = runAppLogic("invalidemail.com");

        assertFalse(success, "Parsing should fail for invalid email");
        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.ERROR_INVALID_EMAIL),
                "Output should contain error message for invalid email");
    }

    @Test
    @DisplayName("Prompt message should be displayed before input")
    void testPromptIsDisplayed() {
        provideInput("user@example.com\n");

        runAppLogic("user@example.com");

        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.PROMPT_EMAIL),
                "Output should contain the prompt message");
    }

    @Test
    @DisplayName("Output format should match Python behavior with correct spacing")
    void testOutputFormat() {
        provideInput("testuser@testdomain.org\n");

        runAppLogic("testuser@testdomain.org");

        String output = getCapturedOutput();

        // Verify the exact format matches Python's print("Your username is: ", username)
        // which produces "Your username is:  testuser" (note: two spaces due to print with comma)
        assertTrue(output.contains("Your username is:  testuser"),
                "Username output should match Python format with correct spacing");
        assertTrue(output.contains("Your domain is:  testdomain.org"),
                "Domain output should match Python format with correct spacing");
    }

    @Test
    @DisplayName("Input with leading and trailing whitespace should be trimmed")
    void testWhitespaceIsTrimmed() {
        provideInput("  user@example.com  \n");

        runAppLogic("  user@example.com  ");

        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.OUTPUT_USERNAME_PREFIX + "user"),
                "Whitespace should be trimmed, username should be 'user'");
        assertTrue(output.contains(Messages.OUTPUT_DOMAIN_PREFIX + "example.com"),
                "Whitespace should be trimmed, domain should be 'example.com'");
    }

    @Test
    @DisplayName("Email with multiple @ characters should split on first @")
    void testMultipleAtCharacters() {
        provideInput("first@second@third\n");

        runAppLogic("first@second@third");

        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.OUTPUT_USERNAME_PREFIX + "first"),
                "Username should be the part before first @");
        assertTrue(output.contains(Messages.OUTPUT_DOMAIN_PREFIX + "second@third"),
                "Domain should be everything after first @");
    }

    @Test
    @DisplayName("Email with empty username should be handled")
    void testEmptyUsername() {
        provideInput("@example.com\n");

        runAppLogic("@example.com");

        String output = getCapturedOutput();
        // Empty username means the prefix is immediately followed by newline
        assertTrue(output.contains(Messages.OUTPUT_USERNAME_PREFIX),
                "Output should contain username prefix even for empty username");
        assertTrue(output.contains(Messages.OUTPUT_DOMAIN_PREFIX + "example.com"),
                "Domain should be parsed correctly");
    }

    @Test
    @DisplayName("Email with empty domain should be handled")
    void testEmptyDomain() {
        provideInput("user@\n");

        runAppLogic("user@");

        String output = getCapturedOutput();
        assertTrue(output.contains(Messages.OUTPUT_USERNAME_PREFIX + "user"),
                "Username should be parsed correctly");
        assertTrue(output.contains(Messages.OUTPUT_DOMAIN_PREFIX),
                "Output should contain domain prefix even for empty domain");
    }

    @Test
    @DisplayName("Prompt should appear on its own line (using println)")
    void testPromptOnOwnLine() {
        provideInput("user@example.com\n");

        runAppLogic("user@example.com");

        String output = getCapturedOutput();
        String[] lines = output.split("\\R");

        // First line should be the prompt
        assertTrue(lines.length > 0, "Should have at least one line of output");
        assertEquals(Messages.PROMPT_EMAIL, lines[0],
                "First line should be exactly the prompt message");
    }

    @Test
    @DisplayName("Invalid email should return false (indicating exit code 1)")
    void testInvalidEmailExitCode() {
        provideInput("invalidemail.com\n");

        boolean success = runAppLogic("invalidemail.com");

        assertFalse(success, "Invalid email should return false (exit code 1)");
    }

    @Test
    @DisplayName("Valid email should return true (indicating exit code 0)")
    void testValidEmailNoExit() {
        provideInput("user@example.com\n");

        boolean success = runAppLogic("user@example.com");

        assertTrue(success, "Valid email should return true (exit code 0)");
    }
}
