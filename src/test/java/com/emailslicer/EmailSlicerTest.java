package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer}, covering all parsing and validation rules
 * specified in the migration specification.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ---- Valid email tests ----

    @Test
    @DisplayName("Valid email returns correct username and domain")
    void slice_validEmail_returnsUsernameAndDomain() {
        EmailSlicer.Result result = slicer.slice("user@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with subdomain returns correct parts")
    void slice_validEmailWithSubdomain_returnsUsernameAndDomain() {
        EmailSlicer.Result result = slicer.slice("user@mail.domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("mail.domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with digits in username")
    void slice_validEmailWithDigits_returnsUsernameAndDomain() {
        EmailSlicer.Result result = slicer.slice("user123@example.org");
        assertEquals("user123", result.getUsername());
        assertEquals("example.org", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with dots in username")
    void slice_validEmailWithDotsInUsername_returnsUsernameAndDomain() {
        EmailSlicer.Result result = slicer.slice("first.last@example.com");
        assertEquals("first.last", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    @DisplayName("Valid email with special characters in username")
    void slice_validEmailWithSpecialChars_returnsUsernameAndDomain() {
        EmailSlicer.Result result = slicer.slice("user+tag@example.com");
        assertEquals("user+tag", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    // ---- Multiple @ symbols ----

    @Test
    @DisplayName("Multiple @ symbols - splits on first @, considers valid")
    void slice_multipleAtSymbols_splitsOnFirst() {
        EmailSlicer.Result result = slicer.slice("user@sub@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    // ---- Invalid email tests ----

    @Test
    @DisplayName("No @ symbol throws IllegalArgumentException")
    void slice_noAtSymbol_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("invalidemail"));
    }

    @Test
    @DisplayName("@ at start (empty username) throws IllegalArgumentException")
    void slice_atAtStart_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("@domain.com"));
    }

    @Test
    @DisplayName("@ at end (empty domain) throws IllegalArgumentException")
    void slice_atAtEnd_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("user@"));
    }

    @Test
    @DisplayName("Empty string throws IllegalArgumentException")
    void slice_emptyString_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(""));
    }

    @Test
    @DisplayName("Null input throws IllegalArgumentException")
    void slice_nullInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice(null));
    }

    @Test
    @DisplayName("Only @ symbol throws IllegalArgumentException")
    void slice_onlyAtSymbol_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("@"));
    }

    // ---- Whitespace trimming tests ----

    @Test
    @DisplayName("Leading and trailing spaces are trimmed before parsing")
    void slice_leadingAndTrailingSpaces_returnsTrimmedResult() {
        EmailSlicer.Result result = slicer.slice("  user@domain.com  ");
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Leading spaces are trimmed before parsing")
    void slice_leadingSpaces_returnsTrimmedResult() {
        EmailSlicer.Result result = slicer.slice("   user@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Trailing spaces are trimmed before parsing")
    void slice_trailingSpaces_returnsTrimmedResult() {
        EmailSlicer.Result result = slicer.slice("user@domain.com   ");
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    @Test
    @DisplayName("Whitespace-only input throws IllegalArgumentException")
    void slice_whitespaceOnly_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> slicer.slice("   "));
    }

    @Test
    @DisplayName("Tabs and spaces trimmed before parsing")
    void slice_tabsAndSpaces_returnsTrimmedResult() {
        EmailSlicer.Result result = slicer.slice("\t user@domain.com \t");
        assertEquals("user", result.getUsername());
        assertEquals("domain.com", result.getDomain());
    }

    // ---- Result object verification ----

    @Test
    @DisplayName("Result object is not null for valid email")
    void slice_validEmail_resultIsNotNull() {
        EmailSlicer.Result result = slicer.slice("test@example.com");
        assertNotNull(result);
        assertNotNull(result.getUsername());
        assertNotNull(result.getDomain());
    }
}
