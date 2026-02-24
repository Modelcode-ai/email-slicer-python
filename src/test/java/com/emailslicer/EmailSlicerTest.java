package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer}.
 */
class EmailSlicerTest {

    @Test
    void slice_validEmail_returnsExpectedUsernameAndDomain() {
        EmailSlicer.Result result = EmailSlicer.slice("avimax37@gmail.com");
        assertEquals("avimax37", result.getUsername());
        assertEquals("gmail.com", result.getDomain());
    }

    @Test
    void slice_missingAtSymbol_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("invalid.email.example")
        );
        assertEquals("Invalid email address", exception.getMessage());
    }

    @Test
    void slice_multipleAtSymbols_splitsOnFirstAt() {
        EmailSlicer.Result result = EmailSlicer.slice("user@sub@domain.com");
        assertEquals("user", result.getUsername());
        assertEquals("sub@domain.com", result.getDomain());
    }

    @Test
    void slice_leadingAndTrailingWhitespace_trimmedBeforeParsing() {
        EmailSlicer.Result result = EmailSlicer.slice("   user@example.com  ");
        assertEquals("user", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void slice_emptyInput_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("")
        );
        assertEquals("Invalid email address", exception.getMessage());
    }

    @Test
    void slice_whitespaceOnlyInput_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice("   ")
        );
        assertEquals("Invalid email address", exception.getMessage());
    }

    @Test
    void slice_nullInput_throwsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.slice(null)
        );
        assertEquals("Invalid email address", exception.getMessage());
    }

    @Test
    void slice_atSymbolAtStart_returnsEmptyUsername() {
        EmailSlicer.Result result = EmailSlicer.slice("@example.com");
        assertEquals("", result.getUsername());
        assertEquals("example.com", result.getDomain());
    }

    @Test
    void slice_atSymbolAtEnd_returnsEmptyDomain() {
        EmailSlicer.Result result = EmailSlicer.slice("user@");
        assertEquals("user", result.getUsername());
        assertEquals("", result.getDomain());
    }

    @Test
    void slice_onlyAtSymbol_returnsEmptyUsernameAndDomain() {
        EmailSlicer.Result result = EmailSlicer.slice("@");
        assertEquals("", result.getUsername());
        assertEquals("", result.getDomain());
    }
}
