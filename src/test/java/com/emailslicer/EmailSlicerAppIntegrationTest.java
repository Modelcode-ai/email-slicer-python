package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for the EmailSlicerApp CLI application.
 * Tests verify the complete flow: prompt, input, parsing, and output.
 */
class EmailSlicerAppIntegrationTest {

    @Test
    void testValidEmail() {
        String input = "avimax37@gmail.com\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(0, exitCode, "Exit code should be 0 for valid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Your username is: avimax37"),
                "Output should contain username");
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Output should contain domain");
    }

    @Test
    void testValidEmailWithWhitespace() {
        String input = " user@example.com \n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(0, exitCode, "Exit code should be 0 for valid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Your username is: user"),
                "Output should contain trimmed username");
        assertTrue(output.contains("Your domain is: example.com"),
                "Output should contain trimmed domain");
    }

    @Test
    void testInvalidEmailMissingAt() {
        String input = "userexample.com\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode, "Exit code should be 1 for invalid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }

    @Test
    void testInvalidEmailLeadingAt() {
        String input = "@example.com\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode, "Exit code should be 1 for invalid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }

    @Test
    void testInvalidEmailTrailingAt() {
        String input = "user@\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode, "Exit code should be 1 for invalid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }

    @Test
    void testInvalidEmailMultipleAt() {
        String input = "user@@domain.com\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode, "Exit code should be 1 for invalid email");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }

    @Test
    void testEmptyInput() {
        String input = "\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode, "Exit code should be 1 for empty input");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }

    @Test
    void testWhitespaceOnlyInput() {
        String input = "   \n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(
                input.getBytes());
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        EmailSlicerApp app = new EmailSlicerApp();
        int exitCode = app.run(inputStream, printStream);

        assertEquals(1, exitCode,
                "Exit code should be 1 for whitespace-only input");
        String output = outputStream.toString();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message");
    }
}
