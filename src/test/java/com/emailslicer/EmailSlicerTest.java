package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailSlicerTest {

    // --- Valid email parsing ---

    @Test
    void parsesValidEmail() {
        EmailSlicer.Result result = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void parsesEmailWithSubdomain() {
        EmailSlicer.Result result = EmailSlicer.parse("user@mail.example.com");
        assertEquals("user", result.getUsername());
        assertEquals("mail.example.com", result.getDomain());
    }

    // --- Invalid email: missing '@' ---

    @Test
    void throwsOnMissingAtSymbol() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.parse("invalidemail"));
        assertTrue(ex.getMessage().contains("@"));
    }

    // --- Edge case: multiple '@' characters ---

    @Test
    void usesFirstAtForSplit() {
        EmailSlicer.Result result = EmailSlicer.parse("user@sub@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    // --- Edge case: leading/trailing whitespace ---

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        EmailSlicer.Result result = EmailSlicer.parse("  user@example.com  ");
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    // --- Edge case: empty input after trim ---

    @Test
    void throwsOnEmptyInputAfterTrim() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.parse("   "));
    }

    @Test
    void throwsOnEmptyString() {
        assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.parse(""));
    }

    // --- Null input ---

    @Test
    void throwsOnNullInput() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> EmailSlicer.parse(null));
        assertEquals("Email must not be null", ex.getMessage());
    }

    // --- Edge cases: empty username or domain (preserve Python behavior) ---

    @Test
    void allowsEmptyUsername() {
        EmailSlicer.Result result = EmailSlicer.parse("@domain.com");
        assertEquals("", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void allowsEmptyDomain() {
        EmailSlicer.Result result = EmailSlicer.parse("user@");
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void allowsAtSignOnly() {
        EmailSlicer.Result result = EmailSlicer.parse("@");
        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }
}
