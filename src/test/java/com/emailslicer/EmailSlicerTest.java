package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for the EmailSlicer class.
 * Tests cover valid email parsing, invalid email detection, and edge cases.
 */
class EmailSlicerTest {

    @Nested
    @DisplayName("Valid Email Parsing Tests")
    class ValidEmailTests {

        @Test
        @DisplayName("Should parse standard email address correctly")
        void parseStandardEmail() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("avimax37@gmail.com");

            assertNotNull(result);
            assertEquals("avimax37", result.username());
            assertEquals("gmail.com", result.domain());
        }

        @Test
        @DisplayName("Should trim leading whitespace from email")
        void parseEmailWithLeadingWhitespace() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("  user@example.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should trim trailing whitespace from email")
        void parseEmailWithTrailingWhitespace() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@example.com  ");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should trim both leading and trailing whitespace from email")
        void parseEmailWithBothWhitespace() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("  user@example.com  ");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should parse email with single character username")
        void parseEmailWithSingleCharUsername() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("a@example.com");

            assertNotNull(result);
            assertEquals("a", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should parse email with single character domain")
        void parseEmailWithSingleCharDomain() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@b");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("b", result.domain());
        }

        @Test
        @DisplayName("Should parse email with dots in username")
        void parseEmailWithDotsInUsername() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("john.doe@example.com");

            assertNotNull(result);
            assertEquals("john.doe", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should parse email with subdomain")
        void parseEmailWithSubdomain() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@mail.example.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("mail.example.com", result.domain());
        }

        @Test
        @DisplayName("Should parse email with numbers in username")
        void parseEmailWithNumbersInUsername() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user123@domain.com");

            assertNotNull(result);
            assertEquals("user123", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("Should parse email with special characters in username")
        void parseEmailWithSpecialCharsInUsername() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user_name+tag@domain.com");

            assertNotNull(result);
            assertEquals("user_name+tag", result.username());
            assertEquals("domain.com", result.domain());
        }
    }

    @Nested
    @DisplayName("Invalid Email Detection Tests")
    class InvalidEmailTests {

        @Test
        @DisplayName("Should return null for email without @ symbol")
        void invalidEmailNoAtSymbol() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("invalid-email");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with @ at the beginning")
        void invalidEmailAtSymbolAtStart() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with @ at the end")
        void invalidEmailAtSymbolAtEnd() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with multiple consecutive @ symbols")
        void invalidEmailMultipleConsecutiveAtSymbols() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with multiple separate @ symbols")
        void invalidEmailMultipleSeparateAtSymbols() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("a@b@c.com");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for empty string input")
        void invalidEmailEmptyString() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for whitespace-only string input")
        void invalidEmailWhitespaceOnly() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("   ");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for null input")
        void invalidEmailNullInput() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail(null);

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email that is just @ symbol")
        void invalidEmailOnlyAtSymbol() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("@");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with @ at start after trimming")
        void invalidEmailAtStartAfterTrim() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("  @example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("Should return null for email with @ at end after trimming")
        void invalidEmailAtEndAfterTrim() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@  ");

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("Should handle email with tab characters as whitespace")
        void parseEmailWithTabWhitespace() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("\tuser@example.com\t");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should handle email with newline characters")
        void parseEmailWithNewlineWhitespace() {
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("\nuser@example.com\n");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should handle very long email username")
        void parseEmailWithLongUsername() {
            String longUsername = "a".repeat(100);
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail(longUsername + "@example.com");

            assertNotNull(result);
            assertEquals(longUsername, result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Should handle very long email domain")
        void parseEmailWithLongDomain() {
            String longDomain = "example" + ".sub".repeat(50) + ".com";
            EmailSlicer.ParsedEmail result = EmailSlicer.parseEmail("user@" + longDomain);

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals(longDomain, result.domain());
        }
    }
}
