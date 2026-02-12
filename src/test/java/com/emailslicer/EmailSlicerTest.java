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

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for EmailSlicer console I/O.
 * These tests verify that the console output matches the Python script exactly.
 */
class EmailSlicerTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;

    @BeforeEach
    void setUp() {
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream((input + "\n").getBytes(StandardCharsets.UTF_8)));
    }

    private String getCapturedOutput() {
        return outputCapture.toString(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Valid email produces username and domain output")
    void validEmail_producesUsernameAndDomain() {
        provideInput("avimax37@gmail.com");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Your username is:  avimax37\n" +
                         "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Invalid email (no @) produces error message")
    void invalidEmail_producesErrorMessage() {
        provideInput("invalid.email.com");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Email with @ at beginning produces empty username")
    void emailWithAtBeginning_producesEmptyUsername() {
        provideInput("@gmail.com");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Your username is:  \n" +
                         "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Email with @ at end produces empty domain")
    void emailWithAtEnd_producesEmptyDomain() {
        provideInput("user@");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Your username is:  user\n" +
                         "Your domain is:  \n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Email with multiple @ splits on first @")
    void emailWithMultipleAt_splitsOnFirst() {
        provideInput("user@sub@domain.com");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Your username is:  user\n" +
                         "Your domain is:  sub@domain.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Empty input produces error message")
    void emptyInput_producesErrorMessage() {
        provideInput("");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Whitespace-only input produces error message")
    void whitespaceOnlyInput_producesErrorMessage() {
        provideInput("   ");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    @DisplayName("Email with leading/trailing whitespace is trimmed")
    void emailWithWhitespace_isTrimmed() {
        provideInput("  avimax37@gmail.com  ");

        EmailSlicer.main(new String[]{});

        String expected = "Please enter your Email Id:\n" +
                         "Your username is:  avimax37\n" +
                         "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }
}
