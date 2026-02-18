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
 * Integration tests for the EmailSlicer CLI application.
 * <p>
 * These tests verify the complete end-to-end behavior of the CLI, including:
 * - Prompt display
 * - Input reading
 * - Output formatting for valid emails
 * - Error message display for invalid emails
 * - Whitespace handling
 * <p>
 * Tests redirect System.in and System.out to simulate user interaction and capture output.
 */
class EmailSlicerCliTest {

    private InputStream originalSystemIn;
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream capturedOutput;

    /**
     * Set up test fixtures before each test.
     * Saves the original System.in and System.out for later restoration.
     */
    @BeforeEach
    void setUp() {
        // Save original streams
        originalSystemIn = System.in;
        originalSystemOut = System.out;

        // Create output capture stream
        capturedOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(capturedOutput));
    }

    /**
     * Restore original System.in and System.out after each test.
     */
    @AfterEach
    void tearDown() {
        // Restore original streams
        System.setIn(originalSystemIn);
        System.setOut(originalSystemOut);
    }

    /**
     * Helper method to simulate user input by redirecting System.in.
     *
     * @param input the simulated user input string
     */
    private void provideInput(String input) {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
    }

    /**
     * Helper method to get the captured output as a string.
     *
     * @return the captured output from System.out
     */
    private String getOutput() {
        return capturedOutput.toString();
    }

    /**
     * Test Case: Valid email input
     * <p>
     * Given: User enters "user@example.com"
     * When: The application runs
     * Then: Should display prompt, username line, and domain line
     */
    @Test
    void testValidEmailInput() {
        // Arrange: Simulate user typing "user@example.com" and pressing Enter
        provideInput("user@example.com\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  user", lines[1], "Second line should show username with two spaces after colon");
        assertEquals("Your domain is:  example.com", lines[2], "Third line should show domain with two spaces after colon");
    }

    /**
     * Test Case: Invalid email input (no @ symbol)
     * <p>
     * Given: User enters "userexample.com" (missing @)
     * When: The application runs
     * Then: Should display prompt and error message
     */
    @Test
    void testInvalidEmailNoAtSymbol() {
        // Arrange: Simulate user typing invalid email without @
        provideInput("userexample.com\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(2, lines.length, "Should have 2 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Please enter a valid Email Id.", lines[1], "Second line should be the error message");
    }

    /**
     * Test Case: Empty email input
     * <p>
     * Given: User enters an empty string
     * When: The application runs
     * Then: Should display prompt and error message
     */
    @Test
    void testEmptyEmailInput() {
        // Arrange: Simulate user pressing Enter without typing anything
        provideInput("\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(2, lines.length, "Should have 2 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Please enter a valid Email Id.", lines[1], "Second line should be the error message");
    }

    /**
     * Test Case: Email with leading and trailing whitespace
     * <p>
     * Given: User enters "   user@example.com   " (with spaces)
     * When: The application runs
     * Then: Should trim whitespace and parse correctly
     */
    @Test
    void testEmailWithWhitespace() {
        // Arrange: Simulate user typing email with leading/trailing spaces
        provideInput("   user@example.com   \n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  user", lines[1], "Should trim whitespace and extract username correctly");
        assertEquals("Your domain is:  example.com", lines[2], "Should trim whitespace and extract domain correctly");
    }

    /**
     * Test Case: Email with only whitespace
     * <p>
     * Given: User enters "     " (only spaces)
     * When: The application runs
     * Then: Should display prompt and error message
     */
    @Test
    void testOnlyWhitespaceInput() {
        // Arrange: Simulate user typing only spaces
        provideInput("     \n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(2, lines.length, "Should have 2 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Please enter a valid Email Id.", lines[1], "Second line should be the error message");
    }

    /**
     * Test Case: Email with empty username (@ at start)
     * <p>
     * Given: User enters "@example.com"
     * When: The application runs
     * Then: Should parse with empty username
     */
    @Test
    void testEmailWithEmptyUsername() {
        // Arrange: Simulate user typing email with @ at the start
        provideInput("@example.com\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  ", lines[1], "Username should be empty (but line still contains two spaces)");
        assertEquals("Your domain is:  example.com", lines[2], "Domain should be correct");
    }

    /**
     * Test Case: Email with empty domain (@ at end)
     * <p>
     * Given: User enters "user@"
     * When: The application runs
     * Then: Should parse with empty domain
     */
    @Test
    void testEmailWithEmptyDomain() {
        // Arrange: Simulate user typing email with @ at the end
        provideInput("user@\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  user", lines[1], "Username should be correct");
        assertEquals("Your domain is:  ", lines[2], "Domain should be empty (but line still contains two spaces)");
    }

    /**
     * Test Case: Email with multiple @ symbols
     * <p>
     * Given: User enters "user@sub@domain.com"
     * When: The application runs
     * Then: Should split on first @ only
     */
    @Test
    void testEmailWithMultipleAtSymbols() {
        // Arrange: Simulate user typing email with multiple @ symbols
        provideInput("user@sub@domain.com\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  user", lines[1], "Username should be before first @");
        assertEquals("Your domain is:  sub@domain.com", lines[2], "Domain should contain everything after first @");
    }

    /**
     * Test Case: Complex valid email
     * <p>
     * Given: User enters a more complex email like "first.last+tag@subdomain.example.com"
     * When: The application runs
     * Then: Should parse correctly splitting on first @
     */
    @Test
    void testComplexValidEmail() {
        // Arrange: Simulate user typing a complex but valid email
        provideInput("first.last+tag@subdomain.example.com\n");

        // Act: Run the CLI application
        EmailSlicer.main(new String[0]);

        // Assert: Verify the complete output
        String output = getOutput();
        String[] lines = output.split(System.lineSeparator());

        assertEquals(3, lines.length, "Should have 3 lines of output");
        assertEquals("Please enter your Email Id:", lines[0], "First line should be the prompt");
        assertEquals("Your username is:  first.last+tag", lines[1], "Username should include all characters before @");
        assertEquals("Your domain is:  subdomain.example.com", lines[2], "Domain should include all characters after @");
    }
}
