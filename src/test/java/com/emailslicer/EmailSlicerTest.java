package com.emailslicer;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmailSlicerTest {

    @Test
    void validEmail() {
        Optional<EmailParts> result = EmailSlicer.parse("avimax37@gmail.com");
        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void multipleAtCharacters() {
        Optional<EmailParts> result = EmailSlicer.parse("user@sub@domain.com");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("sub@domain.com", result.get().domain());
    }

    @Test
    void atAtBeginningIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("@domain.com");
        assertTrue(result.isEmpty());
    }

    @Test
    void atAtEndIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("user@");
        assertTrue(result.isEmpty());
    }

    @Test
    void userAtDomainAtIsValid() {
        Optional<EmailParts> result = EmailSlicer.parse("user@domain@");
        assertTrue(result.isPresent());
        assertEquals("user", result.get().username());
        assertEquals("domain@", result.get().domain());
    }

    @Test
    void singleAtIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("@");
        assertTrue(result.isEmpty());
    }

    @Test
    void whitespaceStripping() {
        Optional<EmailParts> result = EmailSlicer.parse("  avimax37@gmail.com  ".strip());
        assertTrue(result.isPresent());
        assertEquals("avimax37", result.get().username());
        assertEquals("gmail.com", result.get().domain());
    }

    @Test
    void noAtSignIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("noatsignhere");
        assertTrue(result.isEmpty());
    }

    @Test
    void whitespaceOnlyIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("   ".strip());
        assertTrue(result.isEmpty());
    }

    @Test
    void emptyStringIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse("");
        assertTrue(result.isEmpty());
    }

    @Test
    void nullInputIsInvalid() {
        Optional<EmailParts> result = EmailSlicer.parse(null);
        assertTrue(result.isEmpty());
    }
}
