package emailslicer;

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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the EmailSlicer application.
 * <p>
 * Includes both behavioral CLI tests (redirecting System.in/System.out)
 * and unit tests for the extracted parseEmail() helper method.
 */
class EmailSlicerTest {

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
     * Helper to simulate stdin input and invoke main(), returning captured stdout.
     */
    private String runMainWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        capturedOutput.reset();
        EmailSlicer.main(new String[0]);
        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    // ========================================================================
    // Behavioral CLI Tests — test the full main() flow via stdin/stdout
    // ========================================================================

    @Nested
    @DisplayName("Behavioral CLI Tests")
    class BehavioralCliTests {

        @Test
        @DisplayName("Valid email produces correct username and domain output")
        void validEmail() {
            String output = runMainWithInput("avimax37@gmail.com\n");

            assertTrue(output.contains("Please enter your Email Id:"),
                    "Output should contain the input prompt");
            assertTrue(output.contains("Your username is:  avimax37"),
                    "Output should contain username with double space");
            assertTrue(output.contains("Your domain is:  gmail.com"),
                    "Output should contain domain with double space");
        }

        @Test
        @DisplayName("Invalid email (no @) produces error message")
        void invalidEmail() {
            String output = runMainWithInput("invalidemail\n");

            assertTrue(output.contains("Please enter your Email Id:"),
                    "Output should contain the input prompt");
            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Output should contain the error message");
            assertFalse(output.contains("Your username is:"),
                    "Output should NOT contain username line");
            assertFalse(output.contains("Your domain is:"),
                    "Output should NOT contain domain line");
        }

        @Test
        @DisplayName("Whitespace around email is trimmed before parsing")
        void whitespaceStripping() {
            String output = runMainWithInput("  user@domain.com  \n");

            assertTrue(output.contains("Your username is:  user"),
                    "Username should be 'user' after trimming");
            assertTrue(output.contains("Your domain is:  domain.com"),
                    "Domain should be 'domain.com' after trimming");
        }

        @Test
        @DisplayName("Multiple @ symbols — split at first @")
        void multipleAtSymbols() {
            String output = runMainWithInput("first@second@third.com\n");

            assertTrue(output.contains("Your username is:  first"),
                    "Username should be everything before first @");
            assertTrue(output.contains("Your domain is:  second@third.com"),
                    "Domain should be everything after first @");
        }

        @Test
        @DisplayName("Empty input is treated as invalid email")
        void emptyInput() {
            String output = runMainWithInput("\n");

            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Empty input should produce the error message");
        }

        @Test
        @DisplayName("Whitespace-only input is treated as invalid email")
        void whitespaceOnlyInput() {
            String output = runMainWithInput("   \n");

            assertTrue(output.contains("Please enter a valid Email Id."),
                    "Whitespace-only input should produce the error message");
        }

        @Test
        @DisplayName("Exact output format matches Python script for valid email")
        void exactOutputFormat() {
            String output = runMainWithInput("avimax37@gmail.com\n");
            String[] lines = output.split("\\R");

            assertEquals(3, lines.length,
                    "Should have exactly 3 output lines: prompt, username, domain");
            assertEquals("Please enter your Email Id:", lines[0]);
            assertEquals("Your username is:  avimax37", lines[1]);
            assertEquals("Your domain is:  gmail.com", lines[2]);
        }

        @Test
        @DisplayName("Exact output format matches Python script for invalid email")
        void exactOutputFormatInvalid() {
            String output = runMainWithInput("invalidemail\n");
            String[] lines = output.split("\\R");

            assertEquals(2, lines.length,
                    "Should have exactly 2 output lines: prompt, error");
            assertEquals("Please enter your Email Id:", lines[0]);
            assertEquals("Please enter a valid Email Id.", lines[1]);
        }

        @Test
        @DisplayName("EOF input (null) is treated as invalid email")
        void eofInput() {
            // Empty byte array simulates immediate EOF
            String output = runMainWithInput("");

            assertTrue(output.contains("Please enter a valid Email Id."),
                    "EOF input should produce the error message");
        }
    }

    // ========================================================================
    // Unit Tests for parseEmail() — test the extracted logic directly
    // ========================================================================

    @Nested
    @DisplayName("Unit Tests for parseEmail()")
    class ParseEmailUnitTests {

        @Test
        @DisplayName("Valid email returns correct username and domain")
        void validEmail() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("avimax37@gmail.com");

            assertTrue(result.isPresent());
            assertEquals("avimax37", result.get().username());
            assertEquals("gmail.com", result.get().domain());
        }

        @Test
        @DisplayName("Email without @ returns empty Optional")
        void noAtSymbol() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("invalidemail");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Null input returns empty Optional")
        void nullInput() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail(null);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Empty string returns empty Optional")
        void emptyString() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Whitespace-only string returns empty Optional")
        void whitespaceOnly() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("   ");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("Leading/trailing whitespace is trimmed")
        void whitespaceTrimming() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("  user@domain.com  ");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().username());
            assertEquals("domain.com", result.get().domain());
        }

        @Test
        @DisplayName("Multiple @ symbols splits at first @")
        void multipleAtSymbols() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("first@second@third.com");

            assertTrue(result.isPresent());
            assertEquals("first", result.get().username());
            assertEquals("second@third.com", result.get().domain());
        }

        @Test
        @DisplayName("@ at start of string gives empty username")
        void atStart() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("@domain.com");

            assertTrue(result.isPresent());
            assertEquals("", result.get().username());
            assertEquals("domain.com", result.get().domain());
        }

        @Test
        @DisplayName("@ at end of string gives empty domain")
        void atEnd() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("user@");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().username());
            assertEquals("", result.get().domain());
        }

        @Test
        @DisplayName("Just @ gives empty username and domain")
        void justAt() {
            Optional<EmailSlicer.EmailParts> result = EmailSlicer.parseEmail("@");

            assertTrue(result.isPresent());
            assertEquals("", result.get().username());
            assertEquals("", result.get().domain());
        }
    }
}
