package ai.modelcode.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer}, covering normal usage, edge cases, and
 * invalid input scenarios to ensure behavioral parity with the original Python
 * {@code emailSlicer.py} script.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    @Test
    void slice_validEmail_returnsExpectedComponents() {
        EmailComponents result = slicer.slice("user@example.com");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void slice_emailWithLeadingAndTrailingWhitespace_stripsAndParses() {
        EmailComponents result = slicer.slice("  user@example.com  ");

        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void slice_inputWithoutAtSymbol_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    void slice_emptyString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    void slice_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    @Test
    void slice_onlyAtSymbol_returnsBothComponentsEmpty() {
        EmailComponents result = slicer.slice("@");

        assertEquals("", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void slice_missingUsername_returnsEmptyUsername() {
        EmailComponents result = slicer.slice("@example.com");

        assertEquals("", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void slice_missingDomain_returnsEmptyDomain() {
        EmailComponents result = slicer.slice("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void slice_emailWithMultipleAtSymbols_splitsOnFirstAt() {
        // Mirrors Python behavior: email[:email.index("@")] splits at the first @
        EmailComponents result = slicer.slice("user@domain@extra.com");

        assertEquals("user", result.username());
        assertEquals("domain@extra.com", result.domain());
    }

    @Test
    void slice_whitespaceOnlyInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }
}
