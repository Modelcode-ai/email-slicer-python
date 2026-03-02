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
 * CLI integration tests for {@link Main}.
 *
 * <p>Redirects {@code System.in} and {@code System.out} to verify that the
 * application produces the expected output for given inputs.</p>
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

    /**
     * Returns the captured output as a string, normalizing line endings to LF
     * for cross-platform test consistency.
     */
    private String getCapturedOutput() {
        return capturedOutput.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
    }

    @Test
    @DisplayName("CLI: invalid input without @ prints error message")
    void testInvalidInputProducesErrorMessage() {
        String input = "invalidinput\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Expected error message for invalid input, got: " + output);
    }

    @Test
    @DisplayName("CLI: valid input prints username and domain")
    void testValidInputProducesUsernameAndDomain() {
        String input = "avimax37@gmail.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: avimax37"),
                "Expected username line, got: " + output);
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Expected domain line, got: " + output);
    }

    @Test
    @DisplayName("CLI: prompt text is printed before reading input")
    void testPromptIsPrinted() {
        String input = "test@example.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        Main.main(new String[0]);

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Expected prompt text, got: " + output);
    }
}
