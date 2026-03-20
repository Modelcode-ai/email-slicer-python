package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 */
class EmailSlicerTest {

    @Test
    void parseValidEmail() {
        EmailParts result = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals(new EmailParts("avimax37", "gmail.com"), result);
    }

    @Test
    void parseEmailWithSubdomain() {
        EmailParts result = EmailSlicer.parse("user@mail.example.org");
        assertEquals(new EmailParts("user", "mail.example.org"), result);
    }

    @Test
    void parseTrimsLeadingAndTrailingWhitespace() {
        EmailParts result = EmailSlicer.parse("  user@example.org  ");
        assertEquals(new EmailParts("user", "example.org"), result);
    }

    @Test
    void parseRejectsMissingAtSign() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("invalidemail")
        );
        assertEquals(Messages.INVALID_EMAIL, ex.getMessage());
    }

    @Test
    void parseRejectsEmptyString() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("")
        );
    }

    @Test
    void parseRejectsNullInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(null)
        );
    }

    @Test
    void parseRejectsMultipleAtSigns() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("a@b@c.com")
        );
    }

    @Test
    void parseRejectsEmptyUsername() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("@domain.com")
        );
    }

    @Test
    void parseRejectsEmptyDomain() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("user@")
        );
    }

    @Test
    void parseRejectsBlankInput() {
        assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("   ")
        );
    }
}
