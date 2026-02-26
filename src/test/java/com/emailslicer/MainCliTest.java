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
 * CLI integration tests for {@link Main}.
 * Redirects {@code System.in} and {@code System.out} to verify exact output.
 */
class MainCliTest {

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

    @Test
    void validInput_printsUsernameAndDomain() {
        provideInput("avimax37@gmail.com\n");

        Main.main(new String[]{});

        String expected = "Your username is: avimax37\nYour domain is: gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidInput_noAtSymbol_printsErrorMessage() {
        provideInput("invalidemail\n");

        Main.main(new String[]{});

        String expected = "Please enter a valid email address\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void whitespaceInput_trimmedBeforeParsing() {
        provideInput("   user@example.com  \n");

        Main.main(new String[]{});

        String expected = "Your username is: user\nYour domain is: example.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void emptyInput_printsErrorMessage() {
        provideInput("\n");

        Main.main(new String[]{});

        String expected = "Please enter a valid email address\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void multipleAtSymbols_splitsAtFirst() {
        provideInput("user@sub@domain.com\n");

        Main.main(new String[]{});

        String expected = "Your username is: user\nYour domain is: sub@domain.com\n";
        assertEquals(expected, getCapturedOutput());
    }
}
