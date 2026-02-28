package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 */
class EmailSlicerTest {

    @Test
    void slice_validEmail_returnsExpectedParts() {
        EmailParts result = EmailSlicer.slice("user@domain.com");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void slice_readmeExample_returnsExpectedParts() {
        EmailParts result = EmailSlicer.slice("avimax37@gmail.com");

        assertNotNull(result);
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void slice_whitespaceAroundEmail_isTrimmed() {
        EmailParts result = EmailSlicer.slice("  user@domain.com  ");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void slice_missingAtSymbol_returnsNull() {
        assertNull(EmailSlicer.slice("invalidemail"));
    }

    @Test
    void slice_multipleAtSigns_splitsOnFirst() {
        EmailParts result = EmailSlicer.slice("user@sub@domain.com");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void slice_emptyUsername_returnsEmptyUsername() {
        EmailParts result = EmailSlicer.slice("@domain.com");

        assertNotNull(result);
        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void slice_emptyDomain_returnsEmptyDomain() {
        EmailParts result = EmailSlicer.slice("user@");

        assertNotNull(result);
        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void slice_emptyString_returnsNull() {
        assertNull(EmailSlicer.slice(""));
    }

    @Test
    void slice_blankString_returnsNull() {
        assertNull(EmailSlicer.slice("   "));
    }

    @Test
    void slice_nullInput_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice(null));
    }
}
