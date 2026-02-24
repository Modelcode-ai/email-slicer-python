package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} core logic.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid input tests ----

    @Test
    void slicesBasicValidEmail() {
        EmailSlicer.Result result = slicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void slicesEmailWithSubdomain() {
        EmailSlicer.Result result = slicer.slice("user@mail.example.com");
        assertEquals("user", result.getUsername());
        assertEquals("mail.example.com", result.getDomain());
    }

    @Test
    void preservesCase() {
        EmailSlicer.Result result = slicer.slice("John.Doe@Example.COM");
        assertEquals("John.Doe", result.getUsername());
        assertEquals("Example.COM", result.getDomain());
    }

    @Test
    void trimsLeadingAndTrailingWhitespace() {
        EmailSlicer.Result result = slicer.slice("  user@example.com  ");
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void trimsTabsAndNewlines() {
        EmailSlicer.Result result = slicer.slice("\t user@example.com \n");
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void slicesEmailWithSpecialCharactersInUsername() {
        EmailSlicer.Result result = slicer.slice("user.name+tag@example.com");
        assertEquals("user.name+tag", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    // ---- Invalid input tests ----

    @Test
    void rejectsNull() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    @Test
    void rejectsEmptyString() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void rejectsWhitespaceOnly() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    @Test
    void rejectsEmailWithoutAtSymbol() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("userexample.com"));
    }

    @Test
    void rejectsEmailWithAtAtStart() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("@example.com"));
    }

    @Test
    void rejectsEmailWithAtAtEnd() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("user@"));
    }

    @Test
    void rejectsEmailWithMultipleAtSymbols() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("user@@example.com"));
    }

    @Test
    void rejectsEmailWithTwoSeparateAtSymbols() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("user@name@example.com"));
    }
}
