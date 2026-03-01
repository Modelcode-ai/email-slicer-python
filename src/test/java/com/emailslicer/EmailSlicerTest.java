package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} core parsing logic.
 *
 * <p>These tests validate that the Java implementation produces results
 * equivalent to the original Python email slicer for all input categories.</p>
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid email parsing ----

    @Test
    @DisplayName("Valid email: avimax37@gmail.com -> username=avimax37, domain=gmail.com")
    void sliceValidEmail() {
        EmailSlicer.Result result = slicer.slice("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Valid email with subdomain: user@mail.example.co.uk")
    void sliceEmailWithSubdomain() {
        EmailSlicer.Result result = slicer.slice("user@mail.example.co.uk");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("mail.example.co.uk", result.domain());
    }

    // ---- Whitespace trimming ----

    @Test
    @DisplayName("Leading and trailing spaces are trimmed before parsing")
    void sliceEmailWithLeadingAndTrailingSpaces() {
        EmailSlicer.Result result = slicer.slice("  avimax37@gmail.com  ");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    @DisplayName("Tabs and spaces around email are trimmed")
    void sliceEmailWithTabsAndSpaces() {
        EmailSlicer.Result result = slicer.slice("\t avimax37@gmail.com \t");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    // ---- Invalid email (no '@') ----

    @Test
    @DisplayName("Input without '@' returns null")
    void sliceInputWithoutAtReturnsNull() {
        assertNull(slicer.slice("plainaddress"));
    }

    @Test
    @DisplayName("Empty string returns null")
    void sliceEmptyStringReturnsNull() {
        assertNull(slicer.slice(""));
    }

    @Test
    @DisplayName("Whitespace-only string returns null after trimming")
    void sliceWhitespaceOnlyReturnsNull() {
        assertNull(slicer.slice("   "));
    }

    @Test
    @DisplayName("Single space returns null")
    void sliceSingleSpaceReturnsNull() {
        assertNull(slicer.slice(" "));
    }

    // ---- Multiple '@' characters ----

    @Test
    @DisplayName("Multiple '@' splits at the first occurrence: user@sub@domain.com")
    void sliceMultipleAtSplitsAtFirst() {
        EmailSlicer.Result result = slicer.slice("user@sub@domain.com");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    @DisplayName("Three '@' characters: a@b@c@d")
    void sliceThreeAtCharacters() {
        EmailSlicer.Result result = slicer.slice("a@b@c@d");

        assertNotNull(result);
        assertEquals("a", result.username());
        assertEquals("b@c@d", result.domain());
    }

    // ---- Boundary '@' positions ----

    @Test
    @DisplayName("'@' at beginning: @domain.com -> username='', domain='domain.com'")
    void sliceAtBeginning() {
        EmailSlicer.Result result = slicer.slice("@domain.com");

        assertNotNull(result);
        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    @DisplayName("'@' at end: user@ -> username='user', domain=''")
    void sliceAtEnd() {
        EmailSlicer.Result result = slicer.slice("user@");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    @DisplayName("Just '@' alone: username='', domain=''")
    void sliceJustAt() {
        EmailSlicer.Result result = slicer.slice("@");

        assertNotNull(result);
        assertEquals("", result.username());
        assertEquals("", result.domain());
    }

    // ---- Null handling ----

    @Test
    @DisplayName("Null input throws IllegalArgumentException")
    void sliceNullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    @Test
    @DisplayName("Null input exception has descriptive message")
    void sliceNullExceptionMessage() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.slice(null)
        );
        assertEquals("Input cannot be null", ex.getMessage());
    }
}
