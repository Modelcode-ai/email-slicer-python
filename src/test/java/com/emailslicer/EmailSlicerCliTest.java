package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CLI integration tests for EmailSlicer.
 *
 * These tests simulate complete user interaction by capturing stdin/stdout
 * to verify end-to-end behavior including prompts, output formatting, and
 * error messages. This ensures behavioral equivalence with the Python version.
 */
class EmailSlicerCliTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Helper method to normalize line endings for cross-platform compatibility.
     */
    private String normalizeLineEndings(String text) {
        return text.replace("\r\n", "\n").replace("\r", "\n");
    }

    @Test
    void testValidEmailInteraction() {
        // Simulate user input
        String input = "avimax37@gmail.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Run the CLI
        EmailSlicer.main(new String[]{});

        // Capture and normalize output
        String output = normalizeLineEndings(outputStream.toString());

        // Verify exact output format matches Python version
        String expected = "Please enter your Email Id:\n" +
                          "Your username is:  avimax37\n" +
                          "Your domain is:  gmail.com\n";

        assertEquals(expected, output);
    }

    @Test
    void testValidEmailWithSubdomain() {
        String input = "user@mail.example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Your username is:  user\n" +
                          "Your domain is:  mail.example.com\n";

        assertEquals(expected, output);
    }

    @Test
    void testValidEmailWithMinimalParts() {
        String input = "a@b\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Your username is:  a\n" +
                          "Your domain is:  b\n";

        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailWithoutAtSymbol() {
        String input = "invalid-email\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailWithMultipleAtSymbols() {
        String input = "user@@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailStartingWithAt() {
        String input = "@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailEndingWithAt() {
        String input = "user@\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testEmptyInput() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testWhitespaceOnlyInput() {
        String input = "   \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Please enter a valid Email Id.\n";

        assertEquals(expected, output);
    }

    @Test
    void testInputWithLeadingAndTrailingWhitespace() {
        String input = "  user@example.com  \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[]{});

        String output = normalizeLineEndings(outputStream.toString());
        String expected = "Please enter your Email Id:\n" +
                          "Your username is:  user\n" +
                          "Your domain is:  example.com\n";

        assertEquals(expected, output);
    }
}
