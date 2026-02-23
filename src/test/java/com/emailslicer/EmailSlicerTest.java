package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 * <p>
 * Covers valid parsing, whitespace handling, missing {@code @}, multiple {@code @} signs,
 * and empty/null input — mirroring the behavior of the original Python script.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid email parsing ----

    @Test
    void parseValidEmail() {
        EmailParts parts = slicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    @Test
    void parseEmailWithSubdomain() {
        EmailParts parts = slicer.parse("user@mail.example.com");
        assertEquals("user", parts.username());
        assertEquals("mail.example.com", parts.domain());
    }

    @Test
    void parseEmailWithNumericUsername() {
        EmailParts parts = slicer.parse("12345@numbers.org");
        assertEquals("12345", parts.username());
        assertEquals("numbers.org", parts.domain());
    }

    @Test
    void parseEmailWithDotsInUsername() {
        EmailParts parts = slicer.parse("first.last@company.co");
        assertEquals("first.last", parts.username());
        assertEquals("company.co", parts.domain());
    }

    @Test
    void parseEmailWithPlusInUsername() {
        EmailParts parts = slicer.parse("user+tag@example.com");
        assertEquals("user+tag", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- Whitespace handling ----

    @Test
    void parseEmailWithLeadingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void parseEmailWithTrailingWhitespace() {
        EmailParts parts = slicer.parse("user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void parseEmailWithLeadingAndTrailingWhitespace() {
        EmailParts parts = slicer.parse("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void parseEmailWithTabWhitespace() {
        EmailParts parts = slicer.parse("\tuser@example.com\t");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- Missing @ (invalid email) ----

    @Test
    void parseMissingAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("invalidemail"));
    }

    @Test
    void parseMissingAtNoDotsThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("userexample.com"));
    }

    // ---- Multiple @ signs ----

    @Test
    void parseMultipleAtSignsSplitsOnFirst() {
        EmailParts parts = slicer.parse("user@sub@example.com");
        assertEquals("user", parts.username());
        assertEquals("sub@example.com", parts.domain());
    }

    @Test
    void parseDoubleAtInMiddle() {
        EmailParts parts = slicer.parse("name@@domain.com");
        assertEquals("name", parts.username());
        assertEquals("@domain.com", parts.domain());
    }

    // ---- Empty and null input ----

    @Test
    void parseEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse(""));
    }

    @Test
    void parseWhitespaceOnlyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("   "));
    }

    @Test
    void parseNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse(null));
    }

    // ---- Edge cases for empty username or domain ----

    @Test
    void parseEmptyUsernameThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("@domain.com"));
    }

    @Test
    void parseEmptyDomainThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.parse("user@"));
    }
}
