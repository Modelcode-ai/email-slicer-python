package com.emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailSlicerTest {

    // =========================================================================
    // Unit Tests for parseEmail()
    // =========================================================================

    @Nested
    @DisplayName("parseEmail unit tests")
    class ParseEmailTests {

        @Test
        @DisplayName("parses standard email address")
        void parsesStandardEmail() {
            EmailParts parts = EmailSlicer.parseEmail("avimax37@gmail.com");
            assertEquals("avimax37", parts.username());
            assertEquals("gmail.com", parts.domain());
        }

        @Test
        @DisplayName("parses email with subdomain")
        void parsesEmailWithSubdomain() {
            EmailParts parts = EmailSlicer.parseEmail("user@sub.domain.com");
            assertEquals("user", parts.username());
            assertEquals("sub.domain.com", parts.domain());
        }

        @Test
        @DisplayName("parses minimal email")
        void parsesMinimalEmail() {
            EmailParts parts = EmailSlicer.parseEmail("a@b");
            assertEquals("a", parts.username());
            assertEquals("b", parts.domain());
        }

        @Test
        @DisplayName("splits on first @ when multiple @ signs present")
        void splitsOnFirstAtSign() {
            EmailParts parts = EmailSlicer.parseEmail("user@@example.com");
            assertEquals("user", parts.username());
            assertEquals("@example.com", parts.domain());
        }

        @Test
        @DisplayName("parses email with dots in username")
        void parsesEmailWithDotsInUsername() {
            EmailParts parts = EmailSlicer.parseEmail("first.last@example.com");
            assertEquals("first.last", parts.username());
            assertEquals("example.com", parts.domain());
        }

        @Test
        @DisplayName("parses email with plus sign in username")
        void parsesEmailWithPlusInUsername() {
            EmailParts parts = EmailSlicer.parseEmail("user+tag@example.com");
            assertEquals("user+tag", parts.username());
            assertEquals("example.com", parts.domain());
        }
    }

    // =========================================================================
    // Behavioral / CLI Tests using stream redirection
    // =========================================================================

    @Nested
    @DisplayName("CLI behavioral tests")
    class CliTests {

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
            System.setIn(new ByteArrayInputStream(
                    (input + "\n").getBytes(StandardCharsets.UTF_8)));
        }

        private String getCapturedOutput() {
            return capturedOutput.toString(StandardCharsets.UTF_8);
        }

        @Test
        @DisplayName("valid email prints prompt, username, and domain")
        void validEmailProducesCorrectOutput() {
            provideInput("avimax37@gmail.com");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Please enter your Email Id:"),
                    "Output should contain the prompt");
            assertTrue(output.contains("Your username is:  avimax37"),
                    "Output should contain username with double space");
            assertTrue(output.contains("Your domain is:  gmail.com"),
                    "Output should contain domain with double space");
        }

        @Test
        @DisplayName("invalid email without @ prints error message")
        void invalidEmailProducesErrorMessage() {
            provideInput("invalidemail");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Please enter your Email Id:"),
                    "Output should contain the prompt");
            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Output should contain the error message");
        }

        @Test
        @DisplayName("whitespace around email is stripped before parsing")
        void whitespaceIsStrippedFromInput() {
            provideInput("  user@domain.com  ");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Your username is:  user"),
                    "Username should be parsed from trimmed input");
            assertTrue(output.contains("Your domain is:  domain.com"),
                    "Domain should be parsed from trimmed input");
        }

        @Test
        @DisplayName("email with multiple @ signs splits on first @")
        void multipleAtSignsSplitsOnFirst() {
            provideInput("user@@example.com");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Your username is:  user"),
                    "Username should be text before first @");
            assertTrue(output.contains("Your domain is:  @example.com"),
                    "Domain should include remaining @ signs");
        }

        @Test
        @DisplayName("empty input triggers error message")
        void emptyInputTriggersError() {
            provideInput("");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Empty input should produce error message");
        }

        @Test
        @DisplayName("whitespace-only input triggers error message")
        void whitespaceOnlyInputTriggersError() {
            provideInput("   ");

            EmailSlicer.main(new String[]{});

            String output = getCapturedOutput();
            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Whitespace-only input should produce error message");
        }
    }
}
