package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for {@link Main} that verify end-to-end CLI behavior
 * by capturing stdin/stdout and asserting on exact output format.
 */
class MainIntegrationTest {

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

    private String runMainWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        Main.main(new String[]{});
        return capturedOutput.toString(StandardCharsets.UTF_8).stripTrailing();
    }

    @Test
    void testValidEmailOutput() {
        String output = runMainWithInput("avimax37@gmail.com");

        String expected = String.join(System.lineSeparator(),
                "Please enter your Email Id:",
                "Your username is:  avimax37",
                "Your domain is:  gmail.com"
        );
        assertEquals(expected, output);
    }

    @Test
    void testInvalidEmailOutput() {
        String output = runMainWithInput("invalid-email");

        String expected = String.join(System.lineSeparator(),
                "Please enter your Email Id:",
                "Please enter a valid Email Id."
        );
        assertEquals(expected, output);
    }

    @Test
    void testMultipleAtEmailOutput() {
        String output = runMainWithInput("user@sub@domain.com");

        String expected = String.join(System.lineSeparator(),
                "Please enter your Email Id:",
                "Your username is:  user",
                "Your domain is:  sub@domain.com"
        );
        assertEquals(expected, output);
    }

    @Test
    void testWhitespaceInputOutput() {
        String output = runMainWithInput("  user@example.com  ");

        String expected = String.join(System.lineSeparator(),
                "Please enter your Email Id:",
                "Your username is:  user",
                "Your domain is:  example.com"
        );
        assertEquals(expected, output);
    }
}
