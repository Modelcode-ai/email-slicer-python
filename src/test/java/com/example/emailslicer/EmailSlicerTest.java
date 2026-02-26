package com.example.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for {@link EmailSlicer} ensuring behavioral equivalence with the
 * original Python emailSlicer.py script.
 * <p>
 * Each test redirects {@code System.in} and {@code System.out} to verify
 * exact output format, including the double-space after the colon that
 * Python's {@code print("label: ", value)} produces.
 */
class EmailSlicerTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    void setUp() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    /**
     * Helper: simulates stdin with the given string and runs main().
     */
    private void runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        EmailSlicer.main(new String[]{});
    }

    /**
     * Returns captured stdout, normalizing line endings.
     */
    private String capturedOutput() {
        return outContent.toString().replace("\r\n", "\n");
    }

    // -----------------------------------------------------------------------
    // Test 1: Valid email input
    // -----------------------------------------------------------------------
    @Test
    void validEmail_printsUsernameAndDomain() {
        runWithInput("avimax37@gmail.com\n");

        String expected = "Your username is:  avimax37\n" +
                          "Your domain is:  gmail.com\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 2: Invalid email (no @)
    // -----------------------------------------------------------------------
    @Test
    void invalidEmail_noAtSign_printsErrorMessage() {
        runWithInput("invalid-email\n");

        String expected = "Please enter a valid Email Id.\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 3: Leading and trailing whitespace
    // -----------------------------------------------------------------------
    @Test
    void emailWithWhitespace_isTrimmed() {
        runWithInput("   user@domain.com   \n");

        String expected = "Your username is:  user\n" +
                          "Your domain is:  domain.com\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 4: Multiple @ characters — only the first @ is used
    // -----------------------------------------------------------------------
    @Test
    void multipleAtSigns_usesFirstAt() {
        runWithInput("user@sub@domain.com\n");

        String expected = "Your username is:  user\n" +
                          "Your domain is:  sub@domain.com\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 5: Empty input
    // -----------------------------------------------------------------------
    @Test
    void emptyInput_printsErrorMessage() {
        runWithInput("\n");

        String expected = "Please enter a valid Email Id.\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 6: EOF (no input available) — treated as empty input
    // -----------------------------------------------------------------------
    @Test
    void eofInput_printsErrorMessage() {
        // Empty byte array simulates EOF (no newline)
        System.setIn(new ByteArrayInputStream(new byte[0]));
        EmailSlicer.main(new String[]{});

        String expected = "Please enter a valid Email Id.\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 7: Direct sliceEmail method — valid email
    // -----------------------------------------------------------------------
    @Test
    void sliceEmail_validInput_printsCorrectOutput() {
        EmailSlicer.sliceEmail("test@example.org");

        String expected = "Your username is:  test\n" +
                          "Your domain is:  example.org\n";
        assertEquals(expected, capturedOutput());
    }

    // -----------------------------------------------------------------------
    // Test 8: Direct sliceEmail method — null input
    // -----------------------------------------------------------------------
    @Test
    void sliceEmail_nullInput_printsErrorMessage() {
        EmailSlicer.sliceEmail(null);

        String expected = "Please enter a valid Email Id.\n";
        assertEquals(expected, capturedOutput());
    }
}
