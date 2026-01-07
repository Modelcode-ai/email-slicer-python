package com.example.emailslicer;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.security.Permission;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration tests for EmailSlicerApp CLI behavior.
 * Tests capture stdout and intercept System.exit() calls.
 */
class EmailSlicerAppIntegrationTest {

    /**
     * Security manager that prevents System.exit() from terminating the JVM.
     */
    private static class NoExitSecurityManager extends SecurityManager {
        @Override
        public void checkPermission(final Permission perm) {
            // Allow everything
        }

        @Override
        public void checkPermission(final Permission perm, final Object context) {
            // Allow everything
        }

        @Override
        public void checkExit(final int status) {
            super.checkExit(status);
            throw new SecurityException("System.exit(" + status + ") blocked for testing");
        }
    }

    @Test
    void testInteractiveModeWithValidEmail() {
        final String input = "avimax37@gmail.com\n";
        final String output = runAppInteractive(input);

        assertTrue(output.contains("Please enter your Email Id:"));
        assertTrue(output.contains("Your username is: avimax37"));
        assertTrue(output.contains("Your domain is: gmail.com"));
    }

    @Test
    void testInteractiveModeWithInvalidEmail() {
        final String input = "invalid-email\n";
        final String output = runAppInteractive(input);

        assertTrue(output.contains("Please enter your Email Id:"));
        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void testInteractiveModeWithEmailWithWhitespace() {
        final String input = "  test@example.com  \n";
        final String output = runAppInteractive(input);

        assertTrue(output.contains("Please enter your Email Id:"));
        assertTrue(output.contains("Your username is: test"));
        assertTrue(output.contains("Your domain is: example.com"));
    }

    @Test
    void testNonInteractiveModeWithValidEmail() {
        final String output = runAppNonInteractive("user@domain.co.uk");

        // Should not contain prompt in non-interactive mode
        assertTrue(!output.contains("Please enter your Email Id:"));
        assertTrue(output.contains("Your username is: user"));
        assertTrue(output.contains("Your domain is: domain.co.uk"));
    }

    @Test
    void testNonInteractiveModeWithInvalidEmail() {
        final String output = runAppNonInteractive("no-at-sign");

        assertTrue(!output.contains("Please enter your Email Id:"));
        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void testNonInteractiveModeWithEmailAtStart() {
        final String output = runAppNonInteractive("@example.com");

        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    @Test
    void testNonInteractiveModeWithEmailAtEnd() {
        final String output = runAppNonInteractive("user@");

        assertTrue(output.contains("Please enter a valid Email Id."));
    }

    /**
     * Runs the app in interactive mode with simulated stdin.
     *
     * @param input the simulated user input
     * @return the captured stdout output
     */
    private String runAppInteractive(final String input) {
        final InputStream originalIn = System.in;
        final PrintStream originalOut = System.out;
        final SecurityManager originalSecurityManager = System.getSecurityManager();

        try {
            // Install security manager to prevent System.exit()
            System.setSecurityManager(new NoExitSecurityManager());

            // Simulate user input
            final ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
            System.setIn(testIn);

            // Capture output
            final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));

            // Run app - System.exit will throw SecurityException
            try {
                EmailSlicerApp.main(new String[]{});
            } catch (SecurityException e) {
                // Expected from System.exit() call being blocked
            }

            return testOut.toString();
        } finally {
            System.setSecurityManager(originalSecurityManager);
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }

    /**
     * Runs the app in non-interactive mode with command-line argument.
     *
     * @param email the email address to pass as argument
     * @return the captured stdout output
     */
    private String runAppNonInteractive(final String email) {
        final PrintStream originalOut = System.out;
        final SecurityManager originalSecurityManager = System.getSecurityManager();

        try {
            // Install security manager to prevent System.exit()
            System.setSecurityManager(new NoExitSecurityManager());

            // Capture output
            final ByteArrayOutputStream testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));

            // Run app - System.exit will throw SecurityException
            try {
                EmailSlicerApp.main(new String[]{email});
            } catch (SecurityException e) {
                // Expected from System.exit() call being blocked
            }

            return testOut.toString();
        } finally {
            System.setSecurityManager(originalSecurityManager);
            System.setOut(originalOut);
        }
    }
}
