package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * JUnit 5 unit tests for the EmailParser class.
 * Tests both valid email parsing (positive cases) and invalid email rejection (negative cases).
 */
@DisplayName("EmailParser Tests")
class EmailParserTest {

    @Nested
    @DisplayName("Valid Email Parsing Tests")
    class ValidEmailTests {

        @Test
        @DisplayName("should extract username from valid email")
        void shouldExtractUsernameFromValidEmail() {
            ParsedEmail result = EmailParser.parse("avimax37@gmail.com");

            assertNotNull(result);
            assertEquals("avimax37", result.username());
        }

        @Test
        @DisplayName("should extract domain from valid email")
        void shouldExtractDomainFromValidEmail() {
            ParsedEmail result = EmailParser.parse("avimax37@gmail.com");

            assertNotNull(result);
            assertEquals("gmail.com", result.domain());
        }

        @Test
        @DisplayName("should handle email with subdomain")
        void shouldHandleEmailWithSubdomain() {
            ParsedEmail result = EmailParser.parse("user@sub.domain.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("sub.domain.com", result.domain());
        }

        @Test
        @DisplayName("should handle email with special characters in username")
        void shouldHandleEmailWithSpecialCharacters() {
            ParsedEmail result = EmailParser.parse("user.name+tag@example.com");

            assertNotNull(result);
            assertEquals("user.name+tag", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("should trim whitespace from input")
        void shouldTrimWhitespaceFromInput() {
            ParsedEmail result = EmailParser.parse("  user@example.com  ");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("should handle email with leading whitespace only")
        void shouldHandleEmailWithLeadingWhitespaceOnly() {
            ParsedEmail result = EmailParser.parse("   test@domain.org");

            assertNotNull(result);
            assertEquals("test", result.username());
            assertEquals("domain.org", result.domain());
        }

        @Test
        @DisplayName("should handle email with trailing whitespace only")
        void shouldHandleEmailWithTrailingWhitespaceOnly() {
            ParsedEmail result = EmailParser.parse("test@domain.org   ");

            assertNotNull(result);
            assertEquals("test", result.username());
            assertEquals("domain.org", result.domain());
        }

        @Test
        @DisplayName("should handle complex domain with multiple subdomains")
        void shouldHandleComplexDomainWithMultipleSubdomains() {
            ParsedEmail result = EmailParser.parse("admin@mail.server.company.co.uk");

            assertNotNull(result);
            assertEquals("admin", result.username());
            assertEquals("mail.server.company.co.uk", result.domain());
        }

        @Test
        @DisplayName("should handle username with dots")
        void shouldHandleUsernameWithDots() {
            ParsedEmail result = EmailParser.parse("first.middle.last@example.com");

            assertNotNull(result);
            assertEquals("first.middle.last", result.username());
        }

        @Test
        @DisplayName("should handle username with plus sign")
        void shouldHandleUsernameWithPlusSign() {
            ParsedEmail result = EmailParser.parse("user+filter@example.com");

            assertNotNull(result);
            assertEquals("user+filter", result.username());
        }

        @Test
        @DisplayName("should handle username with hyphen")
        void shouldHandleUsernameWithHyphen() {
            ParsedEmail result = EmailParser.parse("user-name@example.com");

            assertNotNull(result);
            assertEquals("user-name", result.username());
        }

        @Test
        @DisplayName("should handle username with underscore")
        void shouldHandleUsernameWithUnderscore() {
            ParsedEmail result = EmailParser.parse("user_name@example.com");

            assertNotNull(result);
            assertEquals("user_name", result.username());
        }
    }

    @Nested
    @DisplayName("Invalid Email Rejection Tests")
    class InvalidEmailTests {

        @Test
        @DisplayName("should reject email without @ symbol")
        void shouldRejectEmailWithoutAtSymbol() {
            ParsedEmail result = EmailParser.parse("invalidemail.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should handle empty input")
        void shouldHandleEmptyInput() {
            ParsedEmail result = EmailParser.parse("");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject email with multiple @ symbols")
        void shouldRejectEmailWithMultipleAtSymbols() {
            ParsedEmail result = EmailParser.parse("user@@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject email with @ at start (empty username)")
        void shouldRejectEmailWithAtAtStart() {
            ParsedEmail result = EmailParser.parse("@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject email with @ at end (empty domain)")
        void shouldRejectEmailWithAtAtEnd() {
            ParsedEmail result = EmailParser.parse("user@");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject null input")
        void shouldRejectNullInput() {
            ParsedEmail result = EmailParser.parse(null);

            assertNull(result);
        }

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n", "  \t\n  "})
        @DisplayName("should reject null, empty, and whitespace-only inputs")
        void shouldRejectNullEmptyAndWhitespaceOnlyInputs(String input) {
            ParsedEmail result = EmailParser.parse(input);

            assertNull(result);
        }

        @Test
        @DisplayName("should reject email with multiple @ in different positions")
        void shouldRejectEmailWithMultipleAtInDifferentPositions() {
            ParsedEmail result = EmailParser.parse("user@domain@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject string with only @ symbol")
        void shouldRejectStringWithOnlyAtSymbol() {
            ParsedEmail result = EmailParser.parse("@");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject whitespace-only input")
        void shouldRejectWhitespaceOnlyInput() {
            ParsedEmail result = EmailParser.parse("     ");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject input with only tabs")
        void shouldRejectInputWithOnlyTabs() {
            ParsedEmail result = EmailParser.parse("\t\t");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject triple @ symbol")
        void shouldRejectTripleAtSymbol() {
            ParsedEmail result = EmailParser.parse("user@@@example.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should reject email with @ and empty parts after trimming")
        void shouldRejectEmailWithAtAndEmptyPartsAfterTrimming() {
            ParsedEmail result = EmailParser.parse("   @   ");

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Edge Case Tests")
    class EdgeCaseTests {

        @Test
        @DisplayName("should handle single character username")
        void shouldHandleSingleCharacterUsername() {
            ParsedEmail result = EmailParser.parse("a@example.com");

            assertNotNull(result);
            assertEquals("a", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("should handle single character domain")
        void shouldHandleSingleCharacterDomain() {
            ParsedEmail result = EmailParser.parse("user@x");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("x", result.domain());
        }

        @Test
        @DisplayName("should handle single character username and domain")
        void shouldHandleSingleCharacterUsernameAndDomain() {
            ParsedEmail result = EmailParser.parse("a@b");

            assertNotNull(result);
            assertEquals("a", result.username());
            assertEquals("b", result.domain());
        }

        @Test
        @DisplayName("should handle numeric username")
        void shouldHandleNumericUsername() {
            ParsedEmail result = EmailParser.parse("12345@example.com");

            assertNotNull(result);
            assertEquals("12345", result.username());
        }

        @Test
        @DisplayName("should handle numeric domain")
        void shouldHandleNumericDomain() {
            ParsedEmail result = EmailParser.parse("user@123.456.789");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("123.456.789", result.domain());
        }
    }
}
