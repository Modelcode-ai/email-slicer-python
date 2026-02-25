package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link Main} CLI entry point.
 *
 * <p>These tests redirect {@code System.in} and {@code System.out} to verify
 * that the end-to-end CLI behavior matches the original Python script.</p>
 */
class MainIntegrationTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;

        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private String getCapturedOutput() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    @Test
    void happyPathValidEmail() {
        provideInput("avimax37@gmail.com\n");

        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the prompt");
        assertTrue(output.contains("Your username is:  avimax37"),
                "Output should contain username with double space");
        assertTrue(output.contains("Your domain is:  gmail.com"),
                "Output should contain domain with double space");
    }

    @Test
    void invalidEmailNoAtSymbol() {
        provideInput("not-an-email\n");

        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the prompt");
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain invalid email message");
    }

    @Test
    void whitespaceHandling() {
        provideInput("   user@example.com  \n");

        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is:  user"),
                "Output should contain trimmed username");
        assertTrue(output.contains("Your domain is:  example.com"),
                "Output should contain trimmed domain");
    }

    @Test
    void emptyInput() {
        provideInput("\n");

        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Empty input should be treated as invalid");
    }

    @Test
    void multipleAtSymbols() {
        provideInput("user@sub@example.com\n");

        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is:  user"),
                "Should split at first @ — username is 'user'");
        assertTrue(output.contains("Your domain is:  sub@example.com"),
                "Should split at first @ — domain includes remaining @");
    }
}
