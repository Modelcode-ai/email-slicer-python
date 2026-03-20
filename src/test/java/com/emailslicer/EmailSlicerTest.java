package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer#slice(String)}.
 */
class EmailSlicerTest {

    @Test
    void validEmail_returnsParsedParts() {
        Optional<EmailParts> result = EmailSlicer.slice("user@example.com");

        assertTrue(result.isPresent());
        assertEquals(new EmailParts("user", "example.com"), result.get());
    }

    @Test
    void validEmail_extractsUsername() {
        Optional<EmailParts> result = EmailSlicer.slice("avimax37@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
    }

    @Test
    void validEmail_extractsDomain() {
        Optional<EmailParts> result = EmailSlicer.slice("avimax37@gmail.com");

        assertTrue(result.isPresent());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void missingAtSign_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("invalidemail.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void emptyInput_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("");

        assertTrue(result.isEmpty());
    }

    @Test
    void nullInput_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice(null);

        assertTrue(result.isEmpty());
    }

    @Test
    void onlyAtSign_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("@");

        assertTrue(result.isEmpty());
    }

    @Test
    void noUsername_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("@domain.com");

        assertTrue(result.isEmpty());
    }

    @Test
    void noDomain_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("user@");

        assertTrue(result.isEmpty());
    }

    @Test
    void whitespaceHandling_trimsThenParses() {
        Optional<EmailParts> result = EmailSlicer.slice("  user@example.com  ");

        assertTrue(result.isPresent());
        assertEquals(new EmailParts("user", "example.com"), result.get());
    }

    @Test
    void multipleAtSigns_returnsEmpty() {
        Optional<EmailParts> result = EmailSlicer.slice("user@@domain.com");

        assertTrue(result.isEmpty());
    }
}
