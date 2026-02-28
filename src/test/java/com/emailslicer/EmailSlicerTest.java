package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Email Slicer application, covering the core slicing logic
 * and the CLI integration behavior.
 */
class EmailSlicerTest {

    @Nested
    @DisplayName("EmailSlicer.slice() — valid inputs")
    class ValidInputTests {

        @Test
        @DisplayName("standard email: avimax37@gmail.com")
        void happyPath() {
            EmailSlicer.Result result = EmailSlicer.slice("avimax37@gmail.com");
            assertEquals("avimax37", result.username());
            assertEquals("gmail.com", result.domain());
        }

        @Test
        @DisplayName("multiple @ signs: split at first @")
        void multipleAtSigns() {
            EmailSlicer.Result result = EmailSlicer.slice("user@name@domain.com");
            assertEquals("user", result.username());
            assertEquals("name@domain.com", result.domain());
        }

        @Test
        @DisplayName("@ at start: empty username")
        void atStart() {
            EmailSlicer.Result result = EmailSlicer.slice("@domain.com");
            assertEquals("", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("@ at end: empty domain")
        void atEnd() {
            EmailSlicer.Result result = EmailSlicer.slice("user@");
            assertEquals("user", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("leading and trailing whitespace is trimmed")
        void whitespaceIsTrimmed() {
            EmailSlicer.Result result = EmailSlicer.slice("  avimax37@gmail.com  ");
            assertEquals("avimax37", result.username());
            assertEquals("gmail.com", result.domain());
        }

        @Test
        @DisplayName("only @ sign")
        void onlyAtSign() {
            EmailSlicer.Result result = EmailSlicer.slice("@");
            assertEquals("", result.username());
            assertEquals("", result.domain());
        }
    }

    @Nested
    @DisplayName("EmailSlicer.slice() — invalid inputs")
    class InvalidInputTests {

        @Test
        @DisplayName("missing @ sign")
        void missingAtSign() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.slice("invalidemail.com")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }

        @Test
        @DisplayName("empty string")
        void emptyString() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.slice("")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }

        @Test
        @DisplayName("whitespace-only string")
        void whitespaceOnly() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.slice("   \t  ")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }

        @Test
        @DisplayName("null input")
        void nullInput() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.slice(null)
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }
    }

    @Nested
    @DisplayName("Main CLI integration")
    class MainIntegrationTests {

        @Test
        @DisplayName("valid email produces correct output matching Python format")
        void validEmailOutput() {
            String input = "avimax37@gmail.com\n";
            String output = runMainWithInput(input);

            assertTrue(output.contains("Please enter your Email Id:"),
                    "Should print the prompt");
            assertTrue(output.contains("Your username is:  avimax37"),
                    "Should print username with double space (matching Python print comma separator)");
            assertTrue(output.contains("Your domain is:  gmail.com"),
                    "Should print domain with double space (matching Python print comma separator)");
        }

        @Test
        @DisplayName("invalid email produces error message")
        void invalidEmailOutput() {
            String input = "invalidemail.com\n";
            String output = runMainWithInput(input);

            assertTrue(output.contains("Please enter your Email Id:"),
                    "Should print the prompt");
            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Should print error message matching Python output");
        }

        /**
         * Runs Main.main() with simulated stdin and captures stdout.
         */
        private String runMainWithInput(String input) {
            InputStream originalIn = System.in;
            PrintStream originalOut = System.out;
            try {
                System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
                ByteArrayOutputStream capture = new ByteArrayOutputStream();
                System.setOut(new PrintStream(capture, true, StandardCharsets.UTF_8));

                Main.main(new String[]{});

                return capture.toString(StandardCharsets.UTF_8);
            } finally {
                System.setIn(originalIn);
                System.setOut(originalOut);
            }
        }
    }
}
