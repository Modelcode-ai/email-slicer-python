package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CLI-level tests for {@link Main} that verify end-to-end behavior
 * including stdin handling, stdout/stderr messages, and exit codes.
 */
class MainCliTest {

    private InputStream inputFrom(String text) {
        return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
    }

    @Test
    void printsUsernameAndDomainForValidEmail() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        int exitCode = Main.run(
                inputFrom("avimax37@gmail.com\n"),
                new PrintStream(out),
                new PrintStream(err)
        );

        assertEquals(0, exitCode);
        String output = out.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Username: avimax37"), "stdout should contain username");
        assertTrue(output.contains("Domain: gmail.com"), "stdout should contain domain");
        assertEquals("", err.toString(StandardCharsets.UTF_8), "stderr should be empty");
    }

    @Test
    void printsErrorForInvalidEmail() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        int exitCode = Main.run(
                inputFrom("invalid-email\n"),
                new PrintStream(out),
                new PrintStream(err)
        );

        assertEquals(1, exitCode);
        assertEquals("", out.toString(StandardCharsets.UTF_8), "stdout should be empty on error");
        String errorOutput = err.toString(StandardCharsets.UTF_8);
        assertTrue(errorOutput.contains("Invalid email address."), "stderr should contain error message");
    }

    @Test
    void printsErrorForEmptyInput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        int exitCode = Main.run(
                inputFrom("\n"),
                new PrintStream(out),
                new PrintStream(err)
        );

        assertEquals(1, exitCode);
        String errorOutput = err.toString(StandardCharsets.UTF_8);
        assertTrue(errorOutput.contains("Invalid email address."), "stderr should contain error message");
    }

    @Test
    void handlesEofInput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        // Empty stream (EOF immediately)
        int exitCode = Main.run(
                inputFrom(""),
                new PrintStream(out),
                new PrintStream(err)
        );

        assertEquals(1, exitCode);
        String errorOutput = err.toString(StandardCharsets.UTF_8);
        assertTrue(errorOutput.contains("Invalid email address."), "stderr should contain error message");
    }

    @Test
    void trimsWhitespaceFromInput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayOutputStream err = new ByteArrayOutputStream();

        int exitCode = Main.run(
                inputFrom("  user@example.com  \n"),
                new PrintStream(out),
                new PrintStream(err)
        );

        assertEquals(0, exitCode);
        String output = out.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("Username: user"), "stdout should contain trimmed username");
        assertTrue(output.contains("Domain: example.com"), "stdout should contain trimmed domain");
    }
}
