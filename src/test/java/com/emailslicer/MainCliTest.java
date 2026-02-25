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
 * CLI integration tests for {@link Main}.
 *
 * <p>These tests simulate stdin input and capture stdout output to verify
 * that the CLI produces output identical to the original Python script.</p>
 */
class MainCliTest {

    private InputStream originalIn;
    private PrintStream originalOut;

    @BeforeEach
    void setUp() {
        originalIn = System.in;
        originalOut = System.out;
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    /**
     * Runs Main.main with the given input string and returns the captured
     * stdout lines, normalized to use {@code \n} line endings.
     *
     * <p>Lines are split on newline boundaries. Trailing empty entries from
     * the split are discarded by {@link String#split}, but each line preserves
     * its own trailing whitespace (important for empty-value output parity
     * with Python's {@code print()}).</p>
     */
    private String[] runMainAndGetLines(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream capture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capture, true, StandardCharsets.UTF_8));

        Main.main(new String[0]);

        String raw = capture.toString(StandardCharsets.UTF_8).replace("\r\n", "\n");
        return raw.split("\n");
    }

    @Test
    void validEmailProducesCorrectOutput() {
        String[] lines = runMainAndGetLines("avimax37@gmail.com\n");

        assertEquals(3, lines.length, "Expected 3 lines: prompt, username, domain");
        assertEquals("Please enter your Email Id:", lines[0]);
        // Note: two spaces between colon and value, matching Python's print() sep behavior
        assertEquals("Your username is:  avimax37", lines[1]);
        assertEquals("Your domain is:  gmail.com", lines[2]);
    }

    @Test
    void invalidEmailProducesErrorMessage() {
        String[] lines = runMainAndGetLines("invalid\n");

        assertEquals(2, lines.length, "Expected 2 lines: prompt, error message");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Please enter a valid Email Id.", lines[1]);
    }

    @Test
    void emailWithWhitespaceIsTrimmed() {
        String[] lines = runMainAndGetLines("  user@example.com  \n");

        assertEquals(3, lines.length);
        assertEquals("Your username is:  user", lines[1]);
        assertEquals("Your domain is:  example.com", lines[2]);
    }

    @Test
    void emailWithMultipleAtSplitsOnFirst() {
        String[] lines = runMainAndGetLines("user@sub@domain.com\n");

        assertEquals(3, lines.length);
        assertEquals("Your username is:  user", lines[1]);
        assertEquals("Your domain is:  sub@domain.com", lines[2]);
    }

    @Test
    void emptyUsernameIsHandled() {
        String[] lines = runMainAndGetLines("@domain.com\n");

        assertEquals(3, lines.length);
        // Empty username — the line should still show the double space after the colon
        assertEquals("Your username is:  ", lines[1]);
        assertEquals("Your domain is:  domain.com", lines[2]);
    }

    @Test
    void emptyDomainIsHandled() {
        String[] lines = runMainAndGetLines("user@\n");

        assertEquals(3, lines.length);
        assertEquals("Your username is:  user", lines[1]);
        // Empty domain — the line should still show the double space after the colon
        assertEquals("Your domain is:  ", lines[2]);
    }
}
