package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for {@link EmailSlicer} CLI behavior.
 *
 * <p>Tests redirect {@code System.in} and {@code System.out} to verify
 * end-to-end behavior of the main method matches expected output.
 */
class EmailSlicerTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private PrintStream originalErr;
    private ByteArrayOutputStream capturedOut;
    private ByteArrayOutputStream capturedErr;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        originalErr = System.err;

        capturedOut = new ByteArrayOutputStream();
        capturedErr = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
        System.setErr(new PrintStream(capturedErr, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(
                (input + System.lineSeparator()).getBytes(StandardCharsets.UTF_8)));
    }

    private String getCapturedOutput() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Valid email should output username and domain")
    void validEmailShouldOutputUsernameAndDomain() {
        provideInput("avimax37@gmail.com");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Should display the prompt. Actual output: " + output);
        assertTrue(output.contains("Your username is avimax37 and your domain is gmail.com"),
                "Should display parsed username and domain. Actual output: " + output);
    }

    @Test
    @DisplayName("Valid email with subdomain should output correctly")
    void validEmailWithSubdomainShouldOutputCorrectly() {
        provideInput("user@mail.example.com");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is user and your domain is mail.example.com"),
                "Should display parsed username and subdomain. Actual output: " + output);
    }

    @Test
    @DisplayName("Invalid email should display error message")
    void invalidEmailShouldDisplayErrorMessage() {
        provideInput("invalid-email");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should display validation error. Actual output: " + output);
    }

    @Test
    @DisplayName("Empty input should display error message")
    void emptyInputShouldDisplayErrorMessage() {
        provideInput("");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should display validation error for empty input. Actual output: " + output);
    }

    @Test
    @DisplayName("Email with only @ should display error message")
    void atOnlyShouldDisplayErrorMessage() {
        provideInput("@");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should display validation error for '@' only. Actual output: " + output);
    }

    @Test
    @DisplayName("Whitespace-only input should display error message")
    void whitespaceOnlyInputShouldDisplayErrorMessage() {
        provideInput("   ");

        EmailSlicer.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should display validation error for whitespace input. Actual output: " + output);
    }
}
