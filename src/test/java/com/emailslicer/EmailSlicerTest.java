package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 *
 * <p>All eight cases from the migration spec are covered, documenting the
 * intentionally simplistic semantics inherited from the original Python script:
 * only the presence of an {@code @} character is checked; no further
 * RFC-compliance validation is performed.</p>
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    /** Case 1: Valid email with a simple domain. */
    @Test
    void testValidSimpleEmail() {
        EmailParts parts = slicer.parse("avimax37@gmail.com");
        assertEquals("avimax37", parts.username());
        assertEquals("gmail.com", parts.domain());
    }

    /** Case 2: Valid email with a subdomain. */
    @Test
    void testValidSubdomainEmail() {
        EmailParts parts = slicer.parse("user@mail.company.org");
        assertEquals("user", parts.username());
        assertEquals("mail.company.org", parts.domain());
    }

    /** Case 3: Leading and trailing whitespace is trimmed before parsing. */
    @Test
    void testWhitespaceTrimming() {
        EmailParts parts = slicer.parse("  user@example.com  ");
        assertEquals("user", parts.username());
        assertEquals("example.com", parts.domain());
    }

    /** Case 4: Input without {@code @} throws {@link InvalidEmailException}. */
    @Test
    void testMissingAtSign() {
        assertThrows(InvalidEmailException.class, () -> slicer.parse("userexample.com"));
    }

    /** Case 5: {@code @} at the start yields an empty username. */
    @Test
    void testAtSignAtStart() {
        EmailParts parts = slicer.parse("@domain.com");
        assertEquals("", parts.username());
        assertEquals("domain.com", parts.domain());
    }

    /** Case 6: {@code @} at the end yields an empty domain. */
    @Test
    void testAtSignAtEnd() {
        EmailParts parts = slicer.parse("user@");
        assertEquals("user", parts.username());
        assertEquals("", parts.domain());
    }

    /**
     * Case 7: Multiple {@code @} symbols — slicing occurs at the <em>first</em>
     * occurrence only, so the second {@code @} becomes part of the domain.
     */
    @Test
    void testMultipleAtSigns() {
        EmailParts parts = slicer.parse("user@@example.com");
        assertEquals("user", parts.username());
        assertEquals("@example.com", parts.domain());
    }

    /** Case 8: Null input throws {@link InvalidEmailException}. */
    @Test
    void testNullInput() {
        assertThrows(InvalidEmailException.class, () -> slicer.parse(null));
    }
}
