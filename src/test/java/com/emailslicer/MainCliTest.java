package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link Main} CLI entry point.
 * <p>
 * These tests exercise the full CLI path by providing input via a
 * {@link ByteArrayInputStream} and capturing output via a
 * {@link ByteArrayOutputStream}, then asserting on the printed text.
 * <p>
 * Tests call {@link Main#run(java.io.InputStream, PrintStream)} directly
 * to avoid {@code System.exit} terminating the test JVM.
 */
class MainCliTest {

    /**
     * Verifies that a valid email address produces the expected prompt,
     * username, and domain output lines, and returns exit code 0.
     */
    @Test
    void validEmailPrintsUsernameAndDomain() {
        String input = "avimax37@gmail.com\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out, true, StandardCharsets.UTF_8);

        int exitCode = Main.run(in, printOut);

        String output = out.toString(StandardCharsets.UTF_8);
        assertEquals(0, exitCode, "Exit code should be 0 for valid input");
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain the prompt");
        assertTrue(output.contains("Your username is:  avimax37"), "Should contain the username");
        assertTrue(output.contains("Your domain is:  gmail.com"), "Should contain the domain");
    }

    /**
     * Verifies that an invalid email (missing {@code @}) produces the
     * error message and returns exit code 1.
     */
    @Test
    void invalidEmailPrintsErrorMessage() {
        String input = "invalidemail\n";
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream printOut = new PrintStream(out, true, StandardCharsets.UTF_8);

        int exitCode = Main.run(in, printOut);

        String output = out.toString(StandardCharsets.UTF_8);
        assertEquals(1, exitCode, "Exit code should be 1 for invalid input");
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain the prompt");
        assertTrue(output.contains("Please enter a valid Email Id."), "Should contain the error message");
        assertFalse(output.contains("Your username is:"), "Should not contain username output");
        assertFalse(output.contains("Your domain is:"), "Should not contain domain output");
    }
}
