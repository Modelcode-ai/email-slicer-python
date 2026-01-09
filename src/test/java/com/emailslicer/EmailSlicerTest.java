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
 * Integration tests for the EmailSlicer CLI.
 *
 * These tests simulate user interaction by replacing System.in and capturing System.out.
 */
class EmailSlicerTest {

    private final InputStream originalSystemIn = System.in;
    private final PrintStream originalSystemOut = System.out;
    private ByteArrayOutputStream outputStream;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    @Test
    void testValidEmailInput() {
        // Simulate user input
        String input = "user@domain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Run the CLI
        EmailSlicer.main(new String[0]);

        // Capture and normalize output
        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        // Verify output contains expected messages
        assertTrue(normalizedOutput.contains("Please enter your Email Id:"),
                "Output should contain prompt");
        assertTrue(normalizedOutput.contains("Your username is: user"),
                "Output should contain username");
        assertTrue(normalizedOutput.contains("Your domain is: domain.com"),
                "Output should contain domain");
        assertFalse(normalizedOutput.contains("Please enter a valid Email Id."),
                "Output should not contain error message for valid input");
    }

    @Test
    void testValidEmailWithSubdomain() {
        String input = "first.last@example.co.uk\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Your username is: first.last"));
        assertTrue(normalizedOutput.contains("Your domain is: example.co.uk"));
    }

    @Test
    void testValidEmailWithLeadingTrailingSpaces() {
        String input = "  user@example.com  \n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Your username is: user"));
        assertTrue(normalizedOutput.contains("Your domain is: example.com"));
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        String input = "userdomain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."));
        assertFalse(normalizedOutput.contains("Your username is:"));
        assertFalse(normalizedOutput.contains("Your domain is:"));
    }

    @Test
    void testInvalidEmailAtSymbolAtStart() {
        String input = "@domain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."));
    }

    @Test
    void testInvalidEmailAtSymbolAtEnd() {
        String input = "user@\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."));
    }

    @Test
    void testInvalidEmailEmptyString() {
        String input = "\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."));
    }

    @Test
    void testInvalidEmailDomainWithoutDot() {
        String input = "user@localhost\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."),
                "Email with domain lacking dot should be rejected");
    }

    @Test
    void testInvalidEmailMultipleAtSymbols() {
        String input = "user@@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."));
    }

    @Test
    void testInvalidEmailWithNonAsciiCharacters() {
        String input = "usér@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        EmailSlicer.main(new String[0]);

        String output = outputStream.toString();
        String normalizedOutput = output.replaceAll("\\r\\n", "\n").trim();

        assertTrue(normalizedOutput.contains("Please enter your Email Id:"));
        assertTrue(normalizedOutput.contains("Please enter a valid Email Id."),
                "Email with non-ASCII characters should be rejected");
    }
}
