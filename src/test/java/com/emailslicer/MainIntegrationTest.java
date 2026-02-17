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

class MainIntegrationTest {

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
        System.setIn(new ByteArrayInputStream((input + "\n").getBytes(StandardCharsets.UTF_8)));
    }

    private String getCapturedOutput() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    @Test
    void validEmail() {
        provideInput("avimax37@gmail.com");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  avimax37\n"
                + "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidEmailNoAtSign() {
        provideInput("noatsignhere");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidEmailAtBeginning() {
        provideInput("@domain.com");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidEmailAtEnd() {
        provideInput("user@");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void validEmailWithLeadingAndTrailingWhitespace() {
        provideInput("  avimax37@gmail.com  ");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  avimax37\n"
                + "Your domain is:  gmail.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidEmailWhitespaceOnly() {
        provideInput("   ");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void validEmailWithMultipleAtSigns() {
        provideInput("user@sub@domain.com");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Your username is:  user\n"
                + "Your domain is:  sub@domain.com\n";
        assertEquals(expected, getCapturedOutput());
    }

    @Test
    void invalidEmailSingleAt() {
        provideInput("@");
        Main.main(new String[]{});

        String expected = "Please enter your Email Id:\n"
                + "Please enter a valid Email Id.\n";
        assertEquals(expected, getCapturedOutput());
    }
}
