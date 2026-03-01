package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} covering all parsing, validation, and
 * edge-case scenarios defined in the migration specification.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // --- Valid email tests ---

    @Test
    void sliceValidEmail_returnsCorrectParts() {
        EmailSlicer.EmailParts result = slicer.slice("avimax37@gmail.com");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void sliceValidEmail_withWhitespace_trimsBothEnds() {
        EmailSlicer.EmailParts result = slicer.slice("  avimax37@gmail.com  ");

        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void sliceEmail_atStart_emptyUsername() {
        EmailSlicer.EmailParts result = slicer.slice("@domain.com");

        assertEquals("", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void sliceEmail_atEnd_emptyDomain() {
        EmailSlicer.EmailParts result = slicer.slice("user@");

        assertEquals("user", result.username());
        assertEquals("", result.domain());
    }

    @Test
    void sliceEmail_multipleAtSigns_splitsAtFirst() {
        EmailSlicer.EmailParts result = slicer.slice("user@sub@domain.com");

        assertEquals("user", result.username());
        assertEquals("sub@domain.com", result.domain());
    }

    @Test
    void sliceEmail_onlyAtSign_emptyUsernameAndDomain() {
        EmailSlicer.EmailParts result = slicer.slice("@");

        assertEquals("", result.username());
        assertEquals("", result.domain());
    }

    // --- Invalid email tests ---

    @Test
    void sliceNullEmail_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.slice(null)
        );
        assertEquals("email must not be null", ex.getMessage());
    }

    @Test
    void sliceEmptyString_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.slice("")
        );
        assertEquals("email must contain '@'", ex.getMessage());
    }

    @Test
    void sliceNoAtSign_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.slice("invalidemail")
        );
        assertEquals("email must contain '@'", ex.getMessage());
    }

    @Test
    void sliceWhitespaceOnly_throwsIllegalArgumentException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> slicer.slice("   ")
        );
        assertEquals("email must contain '@'", ex.getMessage());
    }
}
