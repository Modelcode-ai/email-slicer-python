package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 */
class EmailSlicerTest {

    // ---- Happy-path tests ----

    @Test
    void sliceStandardEmail() {
        EmailParts parts = EmailSlicer.slice("user@example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void sliceEmailWithSubdomain() {
        EmailParts parts = EmailSlicer.slice("user@mail.example.co.uk");
        assertEquals("user", parts.username());
        assertEquals("mail.example.co.uk", parts.domain());
    }

    // ---- Whitespace handling ----

    @Test
    void sliceTrimsLeadingWhitespace() {
        EmailParts parts = EmailSlicer.slice("   user@example.com");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void sliceTrimsTrailingWhitespace() {
        EmailParts parts = EmailSlicer.slice("user@example.com   ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    @Test
    void sliceTrimsBothSides() {
        EmailParts parts = EmailSlicer.slice("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    // ---- Multiple @ characters (split on first) ----

    @Test
    void sliceSplitsOnFirstAtSymbol() {
        EmailParts parts = EmailSlicer.slice("user@name@domain.com");
        assertEquals("user", parts.username());
        assertEquals("name@domain.com", parts.domain());
    }

    // ---- Invalid input tests ----

    @Test
    void sliceRejectsNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice(null)
        );
        assertTrue(ex.getMessage().contains("null"));
    }

    @Test
    void sliceRejectsEmptyString() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("")
        );
        assertTrue(ex.getMessage().contains("empty"));
    }

    @Test
    void sliceRejectsBlankString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("   ")
        );
    }

    @Test
    void sliceRejectsStringWithoutAtSymbol() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("userexample.com")
        );
        assertTrue(ex.getMessage().contains("@"));
    }
}
