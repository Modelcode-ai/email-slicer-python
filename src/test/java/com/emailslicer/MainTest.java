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
 * CLI behavior tests for {@link Main}.
 *
 * <p>Redirects {@code System.in} and {@code System.out} to verify that the
 * console output matches the specification exactly.</p>
 */
class MainTest {

    private final InputStream originalIn = System.in;
    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream capturedOutput;

    @BeforeEach
    void setUp() {
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    @AfterEach
    void tearDown() {
        System.setIn(originalIn);
        System.setOut(originalOut);
    }

    private void provideInput(String input) {
        System.setIn(new ByteArrayInputStream((input + "\n").getBytes()));
    }

    private String getCapturedOutput() {
        return capturedOutput.toString();
    }

    @Test
    void mainWithValidEmail() throws Exception {
        provideInput("avimax37@gmail.com");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        String expected = "Enter your Email: "
                + "Your username is: avimax37" + System.lineSeparator()
                + "Your domain is: gmail.com" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void mainWithInvalidEmail() throws Exception {
        provideInput("bad-email");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        String expected = "Enter your Email: "
                + "Please enter a valid Email Id." + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void mainWithEmptyInput() throws Exception {
        provideInput("");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        String expected = "Enter your Email: "
                + "Please enter a valid Email Id." + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void mainWithWhitespaceEmail() throws Exception {
        provideInput("  user@example.com  ");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        String expected = "Enter your Email: "
                + "Your username is: user" + System.lineSeparator()
                + "Your domain is: example.com" + System.lineSeparator();
        assertEquals(expected, output);
    }

    @Test
    void mainWithMultipleAtSigns() throws Exception {
        provideInput("user@host@domain.com");

        Main.main(new String[0]);

        String output = getCapturedOutput();
        String expected = "Enter your Email: "
                + "Your username is: user" + System.lineSeparator()
                + "Your domain is: host@domain.com" + System.lineSeparator();
        assertEquals(expected, output);
    }
}
