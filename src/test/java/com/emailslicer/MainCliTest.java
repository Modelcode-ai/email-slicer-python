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
 * CLI behavior tests for {@link Main}.
 * <p>
 * Redirects {@code System.in} and {@code System.out} to verify that the
 * CLI interaction matches the original Python emailSlicer.py output exactly.
 */
class MainCliTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOut;

    @BeforeEach
    void setUp() {
        capturedOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOut, true, StandardCharsets.UTF_8));
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
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    // ========== Valid input tests ==========

    @Test
    void validEmailPrintsUsernameAndDomain() {
        provideInput("avimax37@gmail.com");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        // Matches Python output exactly (double space after colon due to print comma behavior)
        assertTrue(output.contains("Please enter your Email Id:"),
                "Should display the prompt");
        assertTrue(output.contains("Your username is:  avimax37"),
                "Should print username with double space");
        assertTrue(output.contains("Your domain is:  gmail.com"),
                "Should print domain with double space");
    }

    @Test
    void validEmailOutputFormat() {
        provideInput("avimax37@gmail.com");
        Main.main(new String[]{});

        String[] lines = getCapturedOutput().split(System.lineSeparator());
        assertEquals(3, lines.length, "Should have exactly 3 output lines");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Your username is:  avimax37", lines[1]);
        assertEquals("Your domain is:  gmail.com", lines[2]);
    }

    @Test
    void validEmailWithSpecialChars() {
        provideInput("user.name+tag@example.co.uk");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is:  user.name+tag"));
        assertTrue(output.contains("Your domain is:  example.co.uk"));
    }

    @Test
    void validEmailWithWhitespace() {
        provideInput("  avimax37@gmail.com  ");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is:  avimax37"));
        assertTrue(output.contains("Your domain is:  gmail.com"));
    }

    // ========== Invalid input tests ==========

    @Test
    void invalidEmailPrintsErrorMessage() {
        provideInput("invalidemail");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Should display the prompt");
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Should print the exact invalid email message");
    }

    @Test
    void invalidEmailOutputFormat() {
        provideInput("invalidemail");
        Main.main(new String[]{});

        String[] lines = getCapturedOutput().split(System.lineSeparator());
        assertEquals(2, lines.length, "Should have exactly 2 output lines for invalid input");
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Please enter a valid Email Id.", lines[1]);
    }

    @Test
    void emptyInputPrintsErrorMessage() {
        provideInput("");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."));
    }
}
