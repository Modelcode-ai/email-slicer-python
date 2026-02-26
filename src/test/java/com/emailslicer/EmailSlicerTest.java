package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer} — validates behavior parity with the
 * original Python emailSlicer.py script.
 */
class EmailSlicerTest {

    // ---------------------------------------------------------------
    // Validation tests
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("isValid")
    class IsValidTests {

        @Test
        @DisplayName("returns true for a standard email address")
        void standardEmail() {
            assertTrue(EmailSlicer.isValid("avimax37@gmail.com"));
        }

        @Test
        @DisplayName("returns false for input without '@'")
        void noAtSign() {
            assertFalse(EmailSlicer.isValid("invalidemail"));
        }

        @Test
        @DisplayName("returns false for empty string")
        void emptyString() {
            assertFalse(EmailSlicer.isValid(""));
        }

        @Test
        @DisplayName("returns false for whitespace-only string")
        void whitespaceOnly() {
            assertFalse(EmailSlicer.isValid("   "));
        }

        @Test
        @DisplayName("returns false for null")
        void nullInput() {
            assertFalse(EmailSlicer.isValid(null));
        }

        @Test
        @DisplayName("returns true when '@' appears multiple times")
        void multipleAtSigns() {
            assertTrue(EmailSlicer.isValid("user@@domain.com"));
        }

        @Test
        @DisplayName("returns true when '@' is at the start")
        void atSignAtStart() {
            assertTrue(EmailSlicer.isValid("@domain.com"));
        }

        @Test
        @DisplayName("returns true when '@' is at the end")
        void atSignAtEnd() {
            assertTrue(EmailSlicer.isValid("user@"));
        }

        @Test
        @DisplayName("returns true for email with leading/trailing whitespace")
        void leadingTrailingWhitespace() {
            assertTrue(EmailSlicer.isValid("  user@example.com  "));
        }
    }

    // ---------------------------------------------------------------
    // Username extraction tests
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("extractUsername")
    class ExtractUsernameTests {

        @Test
        @DisplayName("extracts username from standard email")
        void standardEmail() {
            assertEquals("avimax37", EmailSlicer.extractUsername("avimax37@gmail.com"));
        }

        @Test
        @DisplayName("extracts username splitting at first '@' when multiple exist")
        void multipleAtSigns() {
            assertEquals("user", EmailSlicer.extractUsername("user@@domain.com"));
        }

        @Test
        @DisplayName("returns empty string when '@' is at the start")
        void atSignAtStart() {
            assertEquals("", EmailSlicer.extractUsername("@domain.com"));
        }

        @Test
        @DisplayName("extracts username when '@' is at the end")
        void atSignAtEnd() {
            assertEquals("user", EmailSlicer.extractUsername("user@"));
        }

        @Test
        @DisplayName("trims whitespace before extracting username")
        void leadingTrailingWhitespace() {
            assertEquals("user", EmailSlicer.extractUsername("  user@example.com  "));
        }
    }

    // ---------------------------------------------------------------
    // Domain extraction tests
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("extractDomain")
    class ExtractDomainTests {

        @Test
        @DisplayName("extracts domain from standard email")
        void standardEmail() {
            assertEquals("gmail.com", EmailSlicer.extractDomain("avimax37@gmail.com"));
        }

        @Test
        @DisplayName("extracts domain after first '@' when multiple exist")
        void multipleAtSigns() {
            assertEquals("@domain.com", EmailSlicer.extractDomain("user@@domain.com"));
        }

        @Test
        @DisplayName("extracts domain when '@' is at the start")
        void atSignAtStart() {
            assertEquals("domain.com", EmailSlicer.extractDomain("@domain.com"));
        }

        @Test
        @DisplayName("returns empty string when '@' is at the end")
        void atSignAtEnd() {
            assertEquals("", EmailSlicer.extractDomain("user@"));
        }

        @Test
        @DisplayName("trims whitespace before extracting domain")
        void leadingTrailingWhitespace() {
            assertEquals("example.com", EmailSlicer.extractDomain("  user@example.com  "));
        }
    }

    // ---------------------------------------------------------------
    // Message string constants tests
    // ---------------------------------------------------------------

    @Nested
    @DisplayName("Main message constants")
    class MessageConstantsTests {

        @Test
        @DisplayName("prompt matches expected text")
        void promptText() {
            assertEquals("Enter your Email Id: ", Main.PROMPT);
        }

        @Test
        @DisplayName("invalid email message matches expected text")
        void invalidEmailMessage() {
            assertEquals("Please enter a valid Email Id.", Main.INVALID_EMAIL_MESSAGE);
        }

        @Test
        @DisplayName("username format matches expected text")
        void usernameFormat() {
            assertEquals("Your username is: ", Main.USERNAME_FORMAT);
        }

        @Test
        @DisplayName("domain format matches expected text")
        void domainFormat() {
            assertEquals("Your domain is: ", Main.DOMAIN_FORMAT);
        }
    }
}
