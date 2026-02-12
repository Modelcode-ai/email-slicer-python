package com.emailslicer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailParser.
 * Tests cover all edge cases to ensure functional equivalence with the Python implementation.
 */
class EmailParserTest {

    @Test
    void testValidEmail() {
        EmailParser.Result result = EmailParser.parse("avimax37@gmail.com");

        assertTrue(result.isValid());
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void testInvalidEmailNoAt() {
        EmailParser.Result result = EmailParser.parse("invalid.email.com");

        assertFalse(result.isValid());
    }

    @Test
    void testMultipleAtSymbols() {
        EmailParser.Result result = EmailParser.parse("user@sub@domain.com");

        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    @Test
    void testAtAtBeginning() {
        EmailParser.Result result = EmailParser.parse("@gmail.com");

        assertTrue(result.isValid());
        assertEquals("", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void testAtAtEnd() {
        EmailParser.Result result = EmailParser.parse("user@");

        assertTrue(result.isValid());
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void testEmptyInput() {
        EmailParser.Result result = EmailParser.parse("");

        assertFalse(result.isValid());
    }

    @Test
    void testWhitespaceOnly() {
        EmailParser.Result result = EmailParser.parse("   \t  ");

        assertFalse(result.isValid());
    }

    @Test
    void testWhitespaceHandling() {
        EmailParser.Result result = EmailParser.parse("  avimax37@gmail.com  ");

        assertTrue(result.isValid());
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void testNullInput() {
        EmailParser.Result result = EmailParser.parse(null);

        assertFalse(result.isValid());
    }
}
