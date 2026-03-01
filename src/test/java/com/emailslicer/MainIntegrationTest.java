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
 * Integration tests for {@link Main} that validate end-to-end CLI behavior
 * by simulating stdin and capturing stdout.
 */
class MainIntegrationTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    @Test
    void mainWithValidEmail_printsUsernameAndDomain() {
        String input = "avimax37@gmail.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = capturedOut.toString();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the prompt");
        assertTrue(output.contains("Your username is:  avimax37"),
                "Output should contain the username with double space");
        assertTrue(output.contains("Your domain is:  gmail.com"),
                "Output should contain the domain with double space");
    }

    @Test
    void mainWithInvalidEmail_printsErrorMessage() {
        String input = "invalidemail\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = capturedOut.toString();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the prompt");
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain the invalid email message");
        assertFalse(output.contains("Your username is:"),
                "Output should NOT contain username for invalid email");
        assertFalse(output.contains("Your domain is:"),
                "Output should NOT contain domain for invalid email");
    }

    @Test
    void mainWithValidEmail_outputMatchesExactFormat() {
        String input = "avimax37@gmail.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = capturedOut.toString();
        String[] lines = output.split("\\R");

        assertEquals(3, lines.length, "Should have exactly 3 output lines");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Your username is:  avimax37", lines[1]);
        assertEquals("Your domain is:  gmail.com", lines[2]);
    }

    @Test
    void mainWithInvalidEmail_outputMatchesExactFormat() {
        String input = "invalidemail\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = capturedOut.toString();
        String[] lines = output.split("\\R");

        assertEquals(2, lines.length, "Should have exactly 2 output lines");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Please enter a valid Email Id.", lines[1]);
    }

    @Test
    void mainWithMultipleAtSigns_splitsAtFirstAt() {
        String input = "user@sub@domain.com\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Main.main(new String[]{});

        String output = capturedOut.toString();
        assertTrue(output.contains("Your username is:  user"),
                "Username should be the part before the first @");
        assertTrue(output.contains("Your domain is:  sub@domain.com"),
                "Domain should include everything after the first @");
    }
}
