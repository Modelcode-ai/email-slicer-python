package com.emailslicer;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the EmailSlicer parsing logic.
 */
class EmailSlicerTest {

    @Test
    void testValidEmail() {
        EmailComponents result = EmailSlicer.parseEmail("user@example.com");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void testValidEmailWithSubdomain() {
        EmailComponents result = EmailSlicer.parseEmail("user@mail.example.com");
        assertEquals("user", result.username());
        assertEquals("mail.example.com", result.domain());
    }

    @Test
    void testValidEmailWithWhitespace() {
        EmailComponents result = EmailSlicer.parseEmail("  user@example.com  ");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void testInvalidEmailWithoutAt() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("invalidemail"));
    }

    @Test
    void testInvalidEmailAtStart() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("@example.com"));
    }

    @Test
    void testInvalidEmailAtEnd() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("user@"));
    }

    @Test
    void testInvalidEmptyEmail() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail(""));
    }

    @Test
    void testInvalidWhitespaceOnlyEmail() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail("   "));
    }

    @Test
    void testInvalidNullEmail() {
        assertThrows(IllegalArgumentException.class,
            () -> EmailSlicer.parseEmail(null));
    }

    @Test
    void testEmailWithMultipleAtSymbols() {
        // First @ symbol should be used for splitting
        EmailComponents result = EmailSlicer.parseEmail("user@name@example.com");
        assertEquals("user", result.username());
        assertEquals("name@example.com", result.domain());
    }

    @Test
    void testEmailWithNumbersAndSpecialChars() {
        EmailComponents result = EmailSlicer.parseEmail("user.name+tag123@example-domain.co.uk");
        assertEquals("user.name+tag123", result.username());
        assertEquals("example-domain.co.uk", result.domain());
    }
}
