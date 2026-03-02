package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer} covering valid parsing, invalid input,
 * edge cases, and whitespace trimming to preserve parity with the original
 * Python Email Slicer behavior.
 */
class EmailSlicerTest {

    // ---------------------------------------------------------------
    // 6.1.1 Valid email parsing
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Valid email: avimax37@gmail.com")
    void testValidEmail() {
        EmailSliceResult result = EmailSlicer.slice("avimax37@gmail.com");
        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    // ---------------------------------------------------------------
    // 6.1.2 Invalid email handling
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Invalid email: plain address without @")
    void testInvalidPlainAddress() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.slice("plainaddress"));
    }

    @Test
    @DisplayName("Invalid email: no @ even with surrounding whitespace")
    void testInvalidNoAtWithWhitespace() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.slice("   no-at-here   "));
    }

    // ---------------------------------------------------------------
    // 6.1.3 Edge cases
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Edge case: empty string")
    void testEmptyString() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.slice(""));
    }

    @Test
    @DisplayName("Edge case: whitespace-only input")
    void testWhitespaceOnly() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.slice("   \t  "));
    }

    @Test
    @DisplayName("Edge case: null input")
    void testNullInput() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.slice(null));
    }

    @Test
    @DisplayName("Edge case: @domain.com (empty username)")
    void testEmptyUsername() {
        EmailSliceResult result = EmailSlicer.slice("@domain.com");
        assertNotNull(result);
        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    @DisplayName("Edge case: user@ (empty domain)")
    void testEmptyDomain() {
        EmailSliceResult result = EmailSlicer.slice("user@");
        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    @DisplayName("Edge case: user@@domain.com (multiple @ - first wins)")
    void testMultipleAtSigns() {
        EmailSliceResult result = EmailSlicer.slice("user@@domain.com");
        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("@domain.com", result.domain());
    }

    // ---------------------------------------------------------------
    // 6.1.4 Whitespace trimming
    // ---------------------------------------------------------------

    @Test
    @DisplayName("Whitespace trimming: spaces around valid email")
    void testWhitespaceTrimming() {
        EmailSliceResult result = EmailSlicer.slice("  avimax37@gmail.com  ");
        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }
}
