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
 * Integration-style tests for {@link Main} that verify end-to-end CLI behavior
 * by redirecting {@code System.in} and capturing {@code System.out}.
 */
class MainCliTest {

    private InputStream originalIn;
    private PrintStream originalOut;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));
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
        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    @Test
    void main_validEmail_printsUsernameAndDomain() {
        provideInput("avimax37@gmail.com\n");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain prompt");
        assertTrue(output.contains("Your username is: avimax37"), "Should contain username");
        assertTrue(output.contains("Your domain is: gmail.com"), "Should contain domain");
    }

    @Test
    void main_invalidEmail_printsInvalidMessage() {
        provideInput("invalidemail\n");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain prompt");
        assertTrue(output.contains("Invalid email id"), "Should contain invalid message");
        assertFalse(output.contains("Your username is:"), "Should not contain username label");
        assertFalse(output.contains("Your domain is:"), "Should not contain domain label");
    }

    @Test
    void main_emptyInput_printsInvalidMessage() {
        provideInput("\n");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain prompt");
        assertTrue(output.contains("Invalid email id"), "Should contain invalid message");
    }

    @Test
    void main_emailWithMultipleAtSigns_splitsOnFirst() {
        provideInput("user@sub@domain.com\n");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: user"), "Should contain username before first @");
        assertTrue(output.contains("Your domain is: sub@domain.com"), "Should contain domain after first @");
    }

    @Test
    void main_noInput_printsInvalidMessage() {
        provideInput("");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"), "Should contain prompt");
        assertTrue(output.contains("Invalid email id"), "Should contain invalid message for empty stream");
    }
}
