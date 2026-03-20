package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 */
class EmailSlicerTest {

    @Test
    @DisplayName("Valid email is split into username and domain")
    void validEmail() {
        EmailParts result = EmailSlicer.parse("avimax37@gmail.com");
        assertEquals(new EmailParts("avimax37", "gmail.com"), result);
    }

    @Test
    @DisplayName("Leading and trailing whitespace is trimmed before parsing")
    void leadingTrailingWhitespace() {
        EmailParts result = EmailSlicer.parse("  user@example.org  ");
        assertEquals(new EmailParts("user", "example.org"), result);
    }

    @Test
    @DisplayName("Missing '@' throws IllegalArgumentException")
    void missingAtSign() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("invalidemail")
        );
        assertEquals(Messages.INVALID_EMAIL_MESSAGE, ex.getMessage());
    }

    @Test
    @DisplayName("Empty string throws IllegalArgumentException")
    void emptyString() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(""));
    }

    @Test
    @DisplayName("Null input throws IllegalArgumentException")
    void nullInput() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(null));
    }

    @Test
    @DisplayName("Multiple '@' signs throws IllegalArgumentException")
    void multipleAtSigns() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("a@b@c.com"));
    }

    @Test
    @DisplayName("Empty username (@domain.com) throws IllegalArgumentException")
    void emptyUsername() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("@domain.com"));
    }

    @Test
    @DisplayName("Empty domain (user@) throws IllegalArgumentException")
    void emptyDomain() {
        assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse("user@"));
    }
}
