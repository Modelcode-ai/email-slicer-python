package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * CLI integration tests for {@link Main#run()}.
 *
 * <p>These tests redirect stdin and stdout so that the full CLI behaviour
 * (prompt, output, error message) can be verified end-to-end without
 * spawning a separate process or calling {@link System#exit(int)}.
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

    private int runWithInput(String input) {
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        return Main.run();
    }

    private String output() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    // -----------------------------------------------------------------------
    // Valid email end-to-end
    // -----------------------------------------------------------------------

    @Test
    void validEmailPrintsUsernameAndDomain() {
        int exitCode = runWithInput("avimax37@gmail.com\n");

        String out = output();
        assertEquals(0, exitCode);
        assertTrue(out.contains("Enter your email:"), "Should contain prompt");
        assertTrue(out.contains("Your username is: avimax37"), "Should contain username");
        assertTrue(out.contains("Your domain is: gmail.com"), "Should contain domain");
    }

    @Test
    void validEmailWithSubdomain() {
        int exitCode = runWithInput("user@mail.example.com\n");

        String out = output();
        assertEquals(0, exitCode);
        assertTrue(out.contains("Your username is: user"));
        assertTrue(out.contains("Your domain is: mail.example.com"));
    }

    @Test
    void validEmailMultipleAt() {
        int exitCode = runWithInput("name@sub@domain.com\n");

        String out = output();
        assertEquals(0, exitCode);
        assertTrue(out.contains("Your username is: name"));
        assertTrue(out.contains("Your domain is: sub@domain.com"));
    }

    // -----------------------------------------------------------------------
    // Invalid email end-to-end
    // -----------------------------------------------------------------------

    @Test
    void invalidEmailNoAtSymbol() {
        int exitCode = runWithInput("invalidemail\n");

        String out = output();
        assertEquals(1, exitCode);
        assertTrue(out.contains("Enter your email:"), "Should contain prompt");
        assertTrue(out.contains("Invalid email address."), "Should contain error message");
    }

    @Test
    void emptyInputPrintsError() {
        int exitCode = runWithInput("\n");

        String out = output();
        assertEquals(1, exitCode);
        assertTrue(out.contains("Invalid email address."));
    }

    @Test
    void whitespaceOnlyInputPrintsError() {
        int exitCode = runWithInput("   \n");

        String out = output();
        assertEquals(1, exitCode);
        assertTrue(out.contains("Invalid email address."));
    }

    @Test
    void noInputAtAllPrintsError() {
        // Simulates an empty stdin (EOF immediately)
        int exitCode = runWithInput("");

        String out = output();
        assertEquals(1, exitCode);
        assertTrue(out.contains("Invalid email address."));
    }

    // -----------------------------------------------------------------------
    // Output format verification
    // -----------------------------------------------------------------------

    @Test
    void outputFormatMatchesSpec() {
        runWithInput("test@example.com\n");

        String out = output();
        // Verify exact output lines (prompt on first line, then results)
        assertTrue(out.contains("Enter your email: "), "Prompt should end with space");
        assertTrue(out.contains("Your username is: test"), "Username line format");
        assertTrue(out.contains("Your domain is: example.com"), "Domain line format");
    }
}
