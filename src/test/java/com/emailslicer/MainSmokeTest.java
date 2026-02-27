package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Smoke tests for the {@link Main} CLI entry point.
 *
 * <p>Each test launches {@code Main} in a subprocess, feeds input via stdin,
 * and asserts the expected stdout output to guard against regressions in
 * wiring and output formatting.</p>
 */
class MainSmokeTest {

    private static final String JAVA_BIN = ProcessHandle.current()
            .info()
            .command()
            .orElse("java");

    @Test
    void validEmail_printsUsernameAndDomain() throws Exception {
        ProcessResult result = runMain("avimax37@gmail.com\n");

        assertEquals(0, result.exitCode(), "Expected exit code 0 for valid email");

        String output = result.stdout();
        assertTrue(output.contains("Please enter your Email Id:"),
                "Output should contain the input prompt");
        assertTrue(output.contains("Your username is:  avimax37"),
                "Output should contain username with two spaces after colon");
        assertTrue(output.contains("Your domain is:  gmail.com"),
                "Output should contain domain with two spaces after colon");
    }

    @Test
    void invalidEmail_printsErrorMessage() throws Exception {
        ProcessResult result = runMain("invalidemail\n");

        assertNotEquals(0, result.exitCode(), "Expected non-zero exit code for invalid email");

        String output = result.stdout();
        assertTrue(output.contains("Please enter a valid Email Id."),
                "Output should contain exact error message");
    }

    /**
     * Launches the Main class in a subprocess with the given stdin input
     * and captures stdout, stderr, and exit code.
     */
    private ProcessResult runMain(String stdinInput) throws Exception {
        String classpath = System.getProperty("java.class.path");

        ProcessBuilder pb = new ProcessBuilder(
                JAVA_BIN,
                "-cp", classpath,
                "com.emailslicer.Main"
        );
        pb.redirectErrorStream(false);

        Process process = pb.start();

        // Write input to the process stdin
        try (OutputStream os = process.getOutputStream()) {
            os.write(stdinInput.getBytes(StandardCharsets.UTF_8));
            os.flush();
        }

        // Read stdout
        String stdout;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            stdout = sb.toString();
        }

        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
        assertTrue(finished, "Process should complete within 10 seconds");

        return new ProcessResult(process.exitValue(), stdout);
    }

    private record ProcessResult(int exitCode, String stdout) {
    }
}
