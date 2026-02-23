package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for EmailSlicerService.
 * Tests cover all parsing scenarios from the specification.
 */
class EmailSlicerTest {

    private EmailSlicerService service;

    @BeforeEach
    void setUp() {
        service = new EmailSlicerService();
    }

    @Test
    void testValidEmailParsing() {
        EmailSliceResult result = service.slice("user@domain.com");

        assertTrue(result.valid(), "Email with @ should be valid");
        assertEquals("user", result.username(), "Username should be 'user'");
        assertEquals("domain.com", result.domain(), "Domain should be 'domain.com'");
        assertNull(result.errorMessage(), "Valid email should have no error message");
    }

    @Test
    void testInvalidEmailNoAtSymbol() {
        EmailSliceResult result = service.slice("invalidemail");

        assertFalse(result.valid(), "Email without @ should be invalid");
        assertNull(result.username(), "Invalid email should have null username");
        assertNull(result.domain(), "Invalid email should have null domain");
        assertEquals("Please enter a valid Email Id.", result.errorMessage(),
                "Invalid email should have appropriate error message");
    }

    @Test
    void testWhitespaceHandling() {
        EmailSliceResult result = service.slice("  user@domain.com  ");

        assertTrue(result.valid(), "Whitespace-padded email should be valid");
        assertEquals("user", result.username(), "Username should be trimmed to 'user'");
        assertEquals("domain.com", result.domain(), "Domain should be 'domain.com'");
        assertNull(result.errorMessage(), "Valid email should have no error message");
    }

    @Test
    void testEmptyUsername() {
        EmailSliceResult result = service.slice("@domain.com");

        assertTrue(result.valid(), "Email with empty username should be valid (@ exists)");
        assertEquals("", result.username(), "Username should be empty string");
        assertEquals("domain.com", result.domain(), "Domain should be 'domain.com'");
        assertNull(result.errorMessage(), "Valid email should have no error message");
    }

    @Test
    void testEmptyDomain() {
        EmailSliceResult result = service.slice("user@");

        assertTrue(result.valid(), "Email with empty domain should be valid (@ exists)");
        assertEquals("user", result.username(), "Username should be 'user'");
        assertEquals("", result.domain(), "Domain should be empty string");
        assertNull(result.errorMessage(), "Valid email should have no error message");
    }

    @Test
    void testMultipleAtSymbols() {
        EmailSliceResult result = service.slice("user@sub@domain.com");

        assertTrue(result.valid(), "Email with multiple @ should be valid");
        assertEquals("user", result.username(), "Username should be 'user' (before first @)");
        assertEquals("sub@domain.com", result.domain(),
                "Domain should include everything after first @");
        assertNull(result.errorMessage(), "Valid email should have no error message");
    }

    @Test
    void testNullInput() {
        EmailSliceResult result = service.slice(null);

        assertFalse(result.valid(), "Null input should be invalid");
        assertNull(result.username(), "Null input should have null username");
        assertNull(result.domain(), "Null input should have null domain");
        assertEquals("Please enter a valid Email Id.", result.errorMessage(),
                "Null input should have appropriate error message");
    }

    @Test
    void testEmptyInput() {
        EmailSliceResult result = service.slice("");

        assertFalse(result.valid(), "Empty input should be invalid");
        assertNull(result.username(), "Empty input should have null username");
        assertNull(result.domain(), "Empty input should have null domain");
        assertEquals("Please enter a valid Email Id.", result.errorMessage(),
                "Empty input should have appropriate error message");
    }
}
