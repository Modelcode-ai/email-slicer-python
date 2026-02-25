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
 * CLI behavior tests for {@link App}.
 *
 * <p>Verifies that {@code App.main} produces the exact console messages
 * defined in the modernization specification by redirecting
 * {@code System.in} and {@code System.out}.</p>
 */
class AppTest {

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

    // ---------------------------------------------------------------
    // Valid email input
    // ---------------------------------------------------------------

    @Test
    void validEmail_printsUsernameAndDomain() {
        provideInput("avimax37@gmail.com\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Should print the prompt");
        assertTrue(output.contains("Your username is: avimax37"),
                "Should print the correct username");
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Should print the correct domain");
    }

    @Test
    void validEmail_withWhitespace_printsUsernameAndDomain() {
        provideInput("  user@example.com  \n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: user"),
                "Should print username after trimming whitespace");
        assertTrue(output.contains("Your domain is: example.com"),
                "Should print domain after trimming whitespace");
    }

    // ---------------------------------------------------------------
    // Invalid email input
    // ---------------------------------------------------------------

    @Test
    void invalidEmail_missingAt_printsErrorMessage() {
        provideInput("invalid.email\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should print error message for invalid email");
        assertFalse(output.contains("Your username is:"),
                "Should not print username for invalid email");
        assertFalse(output.contains("Your domain is:"),
                "Should not print domain for invalid email");
    }

    @Test
    void invalidEmail_emptyInput_printsErrorMessage() {
        provideInput("\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should print error message for empty input");
    }

    @Test
    void invalidEmail_multipleAt_printsErrorMessage() {
        provideInput("user@@example.com\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should print error message for email with multiple @");
    }

    // ---------------------------------------------------------------
    // Exact message format verification
    // ---------------------------------------------------------------

    @Test
    void validEmail_exactOutputFormat() {
        provideInput("test@domain.org\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length,
                "Should produce exactly 3 lines: prompt + username + domain");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Your username is: test", lines[1]);
        assertEquals("Your domain is: domain.org", lines[2]);
    }

    @Test
    void invalidEmail_exactOutputFormat() {
        provideInput("no-at-sign\n");

        App.main(new String[]{});

        String output = getCapturedOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(2, lines.length,
                "Should produce exactly 2 lines: prompt + error");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Please enter a valid Email Id.", lines[1]);
    }
}
