package com.modelcode.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmailSlicerTest {

    @Test
    void parseValidBasicEmail() {
        EmailParts parts = EmailSlicer.parse("alice@example.com");
        assertEquals("alice", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }

    @Test
    void parseValidEmailWithSurroundingWhitespace() {
        EmailParts parts = EmailSlicer.parse("  bob@domain.org  ");
        assertEquals("bob", parts.getUsername());
        assertEquals("domain.org", parts.getDomain());
    }

    @Test
    void parseValidEmailWithLeadingWhitespace() {
        EmailParts parts = EmailSlicer.parse("   carol@test.io");
        assertEquals("carol", parts.getUsername());
        assertEquals("test.io", parts.getDomain());
    }

    @Test
    void parseValidEmailWithSubdomain() {
        EmailParts parts = EmailSlicer.parse("user@mail.example.co.uk");
        assertEquals("user", parts.getUsername());
        assertEquals("mail.example.co.uk", parts.getDomain());
    }

    @Test
    void parseValidEmailWithDotsInUsername() {
        EmailParts parts = EmailSlicer.parse("first.last@example.com");
        assertEquals("first.last", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }

    @Test
    void parseNullInputThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse(null)
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseEmptyStringThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseWhitespaceOnlyThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("   ")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseMissingAtSymbolThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("alice.example.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseMultipleAtSymbolsThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("a@b@c.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseEmptyUsernameThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("@example.com")
        );
        assertNotNull(ex.getMessage());
    }

    @Test
    void parseEmptyDomainThrowsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> EmailSlicer.parse("alice@")
        );
        assertNotNull(ex.getMessage());
    }
}
