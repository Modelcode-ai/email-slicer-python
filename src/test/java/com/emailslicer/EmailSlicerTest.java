package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the EmailSlicer class.
 * Covers valid email parsing, edge cases, and invalid inputs.
 */
class EmailSlicerTest {

    // ============ Valid Email Parsing Tests ============

    @Test
    @DisplayName("Valid email: standard format user@domain.com")
    void testValidEmailStandardFormat() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("user@domain.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with subdomain: user@sub.domain.com")
    void testValidEmailWithSubdomain() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("user@sub.domain.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("sub.domain.com", result.getDomain());
    }

    // ============ Whitespace Trimming Tests ============

    @Test
    @DisplayName("Whitespace trimming: leading spaces")
    void testWhitespaceTrimLeading() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("   user@domain.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Whitespace trimming: trailing spaces")
    void testWhitespaceTrimTrailing() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("user@domain.com   ");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Whitespace trimming: both leading and trailing spaces")
    void testWhitespaceTrimBoth() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("  user@domain.com  ");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Whitespace trimming: tabs and newlines")
    void testWhitespaceTrimTabsAndNewlines() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("\t user@domain.com \n");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    // ============ Multiple @ Symbol Tests ============

    @Test
    @DisplayName("Multiple @ symbols: split on first @ only")
    void testMultipleAtSymbols() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("user@sub@domain.com");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Multiple @ symbols: three @ characters")
    void testThreeAtSymbols() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("a@b@c@d");

        assertNotNull(result);
        assertEquals("a", result.getUsername());
        assertEquals("b@c@d", result.getDomain());
    }

    // ============ Edge Cases with Empty Parts ============

    @Test
    @DisplayName("Edge case: empty username (@domain.com)")
    void testEmptyUsername() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("@domain.com");

        assertNotNull(result);
        assertEquals("", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Edge case: empty domain (user@)")
    void testEmptyDomain() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("user@");

        assertNotNull(result);
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    @DisplayName("Edge case: only @ symbol")
    void testOnlyAtSymbol() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("@");

        assertNotNull(result);
        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }

    // ============ Invalid Input Tests ============

    @Test
    @DisplayName("Invalid: no @ symbol")
    void testInvalidNoAtSymbol() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("userdomain.com");

        assertNull(result);
    }

    @Test
    @DisplayName("Invalid: plain text without @")
    void testInvalidPlainText() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("plaintext");

        assertNull(result);
    }

    @Test
    @DisplayName("Invalid: whitespace-only input")
    void testInvalidWhitespaceOnly() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("   ");

        assertNull(result);
    }

    @Test
    @DisplayName("Invalid: empty string")
    void testInvalidEmptyString() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("");

        assertNull(result);
    }

    @Test
    @DisplayName("Invalid: null input")
    void testInvalidNullInput() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail(null);

        assertNull(result);
    }

    @Test
    @DisplayName("Invalid: tabs and newlines only")
    void testInvalidTabsAndNewlinesOnly() {
        EmailSlicer.EmailParts result = EmailSlicer.parseEmail("\t\n  \t");

        assertNull(result);
    }
}
