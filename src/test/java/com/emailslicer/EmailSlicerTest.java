package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 * <p>
 * All test expectations are derived from running the original Python
 * emailSlicer.py script to ensure behavioral parity.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ========== Valid email parsing ==========

    @Test
    void sliceValidEmail() {
        // README example: avimax37@gmail.com
        EmailSlicer.Result result = slicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void sliceEmailWithDotsAndPlusTag() {
        EmailSlicer.Result result = slicer.slice("user.name+tag@example.co.uk");
        assertEquals("user.name+tag", result.getUsername());
        assertEquals("example.co.uk", result.getDomain());
    }

    @Test
    void sliceMinimalEmail() {
        EmailSlicer.Result result = slicer.slice("a@b");
        assertEquals("a", result.getUsername());
        assertEquals("b", result.getDomain());
    }

    // ========== Whitespace handling (mirrors Python's strip()) ==========

    @Test
    void sliceTrimsLeadingAndTrailingWhitespace() {
        EmailSlicer.Result result = slicer.slice("  avimax37@gmail.com  ");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void sliceTrimsTabsAndNewlines() {
        EmailSlicer.Result result = slicer.slice("\tavimax37@gmail.com\n");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    // ========== Python parity: edge cases that Python treats as valid ==========

    @Test
    void sliceAtStartYieldsEmptyUsername() {
        // Python: @domain.com → username="", domain="domain.com"
        EmailSlicer.Result result = slicer.slice("@domain.com");
        assertEquals("", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    void sliceAtEndYieldsEmptyDomain() {
        // Python: user@ → username="user", domain=""
        EmailSlicer.Result result = slicer.slice("user@");
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void sliceMultipleAtSlicesOnFirst() {
        // Python: user@@domain.com → username="user", domain="@domain.com"
        EmailSlicer.Result result = slicer.slice("user@@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("@domain.com", result.getDomain());
    }

    @Test
    void sliceEmailWithMultipleAtSigns() {
        // Python: user@middle@domain.com → username="user", domain="middle@domain.com"
        EmailSlicer.Result result = slicer.slice("user@middle@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("middle@domain.com", result.getDomain());
    }

    // ========== Invalid email handling ==========

    @Test
    void sliceNoAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    void sliceEmptyStringThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void sliceWhitespaceOnlyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    @Test
    void sliceNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }
}
