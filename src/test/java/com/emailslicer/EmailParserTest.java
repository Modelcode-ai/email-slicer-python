package com.emailslicer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Comprehensive unit tests for EmailParser class.
 */
class EmailParserTest {

    private final EmailParser parser = new EmailParser();

    // Valid email test cases

    @Test
    void testValidEmail_Standard() {
        EmailParts result = parser.parseEmail("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }

    @Test
    void testValidEmail_WithLeadingAndTrailingWhitespace() {
        EmailParts result = parser.parseEmail(" user@example.com ");
        assertEquals("user", result.username());
        assertEquals("example.com", result.domain());
    }

    @Test
    void testValidEmail_WithMultipleDomainParts() {
        EmailParts result = parser.parseEmail("test@domain.co.uk");
        assertEquals("test", result.username());
        assertEquals("domain.co.uk", result.domain());
    }

    @Test
    void testValidEmail_SingleCharacterUsername() {
        EmailParts result = parser.parseEmail("a@domain.com");
        assertEquals("a", result.username());
        assertEquals("domain.com", result.domain());
    }

    @Test
    void testValidEmail_SingleCharacterDomain() {
        EmailParts result = parser.parseEmail("user@d");
        assertEquals("user", result.username());
        assertEquals("d", result.domain());
    }

    // Invalid email test cases - null and empty

    @Test
    void testInvalidEmail_Null() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail(null));
    }

    @Test
    void testInvalidEmail_EmptyString() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail(""));
    }

    @Test
    void testInvalidEmail_WhitespaceOnly() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("   "));
    }

    // Invalid email test cases - missing @

    @Test
    void testInvalidEmail_MissingAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("userexample.com"));
    }

    // Invalid email test cases - @ at boundaries

    @Test
    void testInvalidEmail_LeadingAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("@example.com"));
    }

    @Test
    void testInvalidEmail_TrailingAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("user@"));
    }

    @Test
    void testInvalidEmail_OnlyAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("@"));
    }

    // Invalid email test cases - multiple @

    @Test
    void testInvalidEmail_DoubleAtSymbol() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("user@@domain.com"));
    }

    @Test
    void testInvalidEmail_MultipleAtSymbols() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("user@domain@com"));
    }

    @Test
    void testInvalidEmail_ThreeAtSymbols() {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail("user@@domain@com"));
    }

    // Parameterized tests for invalid inputs

    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "   ",
        "userexample.com",
        "@example.com",
        "user@",
        "@",
        "user@@domain.com",
        "user@domain@com",
        "@@@"
    })
    void testInvalidEmails(String email) {
        assertThrows(IllegalArgumentException.class,
                () -> parser.parseEmail(email));
    }
}
