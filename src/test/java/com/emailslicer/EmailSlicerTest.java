package com.emailslicer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} parsing logic.
 */
class EmailSlicerTest {

    @Test
    void sliceValidEmail() {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", parts.getUsername());
        assertEquals("gmail.com", parts.getDomain());
    }

    @Test
    void sliceMissingAtThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            EmailSlicer.slice("invalidemailaddress");
        });
    }

    @Test
    void sliceMultipleAtCharactersSplitsAtFirst() {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("user@sub@domain.com");

        assertEquals("user", parts.getUsername());
        assertEquals("sub@domain.com", parts.getDomain());
    }

    @Test
    void sliceEmptyUsername() {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("@domain.com");

        assertEquals("", parts.getUsername());
        assertEquals("domain.com", parts.getDomain());
    }

    @Test
    void sliceEmptyDomain() {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("user@");

        assertEquals("user", parts.getUsername());
        assertEquals("", parts.getDomain());
    }

    @Test
    void sliceTrimsLeadingAndTrailingWhitespace() {
        EmailSlicer.EmailParts parts = EmailSlicer.slice("   user@example.com  ");

        assertEquals("user", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }
}
