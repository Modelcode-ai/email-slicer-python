package emailslicer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the {@link Main} class.
 * <p>
 * These tests simulate CLI usage by redirecting {@code System.in} and capturing
 * {@code System.out}. All assertions are against stdout only, matching the Python
 * script's behavior of printing all messages via {@code print()}.
 */
class MainIntegrationTest {

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
        System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    private String getCapturedOutput() {
        return capturedOut.toString(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("prints username and domain for valid email")
    void validEmailOutput() {
        provideInput("avimax37@gmail.com\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: avimax37"),
                "Output should contain username line. Got: " + output);
        assertTrue(output.contains("Your domain is: gmail.com"),
                "Output should contain domain line. Got: " + output);
    }

    @Test
    @DisplayName("prints prompt before reading input")
    void printsPrompt() {
        provideInput("avimax37@gmail.com\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the input prompt. Got: " + output);
    }

    @Test
    @DisplayName("prints error message for invalid email without @")
    void invalidEmailOutput() {
        provideInput("plainaddress\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain error message. Got: " + output);
    }

    @Test
    @DisplayName("output format matches expected line structure for valid email")
    void outputFormatValidEmail() {
        provideInput("user@example.org\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        String[] lines = output.trim().split("\\R");

        // Expect: prompt line, username line, domain line
        assertTrue(lines.length >= 3,
                "Expected at least 3 lines of output. Got: " + output);
        assertEquals("Please enter your Email Id:", lines[0]);
        assertEquals("Your username is: user", lines[1]);
        assertEquals("Your domain is: example.org", lines[2]);
    }

    @Test
    @DisplayName("handles email with empty username")
    void emptyUsernameEmail() {
        provideInput("@domain.com\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: "),
                "Output should contain username line. Got: " + output);
        assertTrue(output.contains("Your domain is: domain.com"),
                "Output should contain domain line. Got: " + output);
    }

    @Test
    @DisplayName("handles email with empty domain")
    void emptyDomainEmail() {
        provideInput("user@\n");
        Main.main(new String[]{});

        String output = getCapturedOutput();
        assertTrue(output.contains("Your username is: user"),
                "Output should contain username line. Got: " + output);
        assertTrue(output.contains("Your domain is: "),
                "Output should contain domain line. Got: " + output);
    }
}
