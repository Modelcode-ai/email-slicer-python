package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EmailParser.sliceEmail() method.
 * Tests cover all parsing scenarios to ensure behavioral parity with the Python implementation.
 */
class EmailParserTest {

    @Nested
    @DisplayName("Valid email addresses")
    class ValidEmails {

        @Test
        @DisplayName("should parse a standard email address with single @ symbol")
        void shouldParseStandardEmail() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("user@domain.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("should parse email with complex username")
        void shouldParseEmailWithComplexUsername() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("john.doe+test@example.org");

            assertNotNull(result);
            assertEquals("john.doe+test", result.username());
            assertEquals("example.org", result.domain());
        }

        @Test
        @DisplayName("should parse email with subdomain")
        void shouldParseEmailWithSubdomain() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("admin@mail.company.co.uk");

            assertNotNull(result);
            assertEquals("admin", result.username());
            assertEquals("mail.company.co.uk", result.domain());
        }
    }

    @Nested
    @DisplayName("Edge cases with @ symbol")
    class EdgeCasesWithAtSymbol {

        @Test
        @DisplayName("should handle email starting with @ (empty username)")
        void shouldHandleEmailStartingWithAt() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("@domain.com");

            assertNotNull(result);
            assertEquals("", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("should handle email ending with @ (empty domain)")
        void shouldHandleEmailEndingWithAt() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("user@");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("should handle email with only @ symbol")
        void shouldHandleOnlyAtSymbol() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("@");

            assertNotNull(result);
            assertEquals("", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("should split on first @ when multiple @ symbols present")
        void shouldSplitOnFirstAtWithMultipleAtSymbols() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("user@sub@domain.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("sub@domain.com", result.domain());
        }

        @Test
        @DisplayName("should handle many @ symbols correctly")
        void shouldHandleManyAtSymbols() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("a@b@c@d@e");

            assertNotNull(result);
            assertEquals("a", result.username());
            assertEquals("b@c@d@e", result.domain());
        }
    }

    @Nested
    @DisplayName("Invalid email addresses (no @ symbol)")
    class InvalidEmails {

        @Test
        @DisplayName("should return null for email without @ symbol")
        void shouldReturnNullForEmailWithoutAt() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("userdomain.com");

            assertNull(result);
        }

        @Test
        @DisplayName("should return null for empty string")
        void shouldReturnNullForEmptyString() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("");

            assertNull(result);
        }

        @Test
        @DisplayName("should return null for null input")
        void shouldReturnNullForNullInput() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail(null);

            assertNull(result);
        }

        @Test
        @DisplayName("should return null for plain text without @")
        void shouldReturnNullForPlainText() {
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("just some text");

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("Whitespace handling (parser expects pre-trimmed input)")
    class WhitespaceHandling {

        @Test
        @DisplayName("should parse pre-trimmed email correctly")
        void shouldParsePreTrimmedEmail() {
            // Simulating what happens after trim() is called in the I/O layer
            String trimmedEmail = "   user@domain.com  ".trim();
            EmailParser.ParsedEmail result = EmailParser.sliceEmail(trimmedEmail);

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("should preserve internal whitespace in username")
        void shouldPreserveInternalWhitespaceInUsername() {
            // Note: This is an unusual case, but testing parser behavior
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("user name@domain.com");

            assertNotNull(result);
            assertEquals("user name", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("should preserve internal whitespace in domain")
        void shouldPreserveInternalWhitespaceInDomain() {
            // Note: This is an unusual case, but testing parser behavior
            EmailParser.ParsedEmail result = EmailParser.sliceEmail("user@domain name.com");

            assertNotNull(result);
            assertEquals("user", result.username());
            assertEquals("domain name.com", result.domain());
        }

        @Test
        @DisplayName("whitespace-only input after trim becomes empty string")
        void whitespaceOnlyInputAfterTrimBecomesEmpty() {
            // Simulating whitespace-only input that gets trimmed in I/O layer
            String trimmedInput = "   ".trim();
            EmailParser.ParsedEmail result = EmailParser.sliceEmail(trimmedInput);

            assertNull(result);
        }
    }

    @Nested
    @DisplayName("ParsedEmail record behavior")
    class ParsedEmailRecordBehavior {

        @Test
        @DisplayName("should have correct equals behavior")
        void shouldHaveCorrectEqualsBehavior() {
            EmailParser.ParsedEmail email1 = EmailParser.sliceEmail("user@domain.com");
            EmailParser.ParsedEmail email2 = EmailParser.sliceEmail("user@domain.com");

            assertEquals(email1, email2);
        }

        @Test
        @DisplayName("should have correct hashCode behavior")
        void shouldHaveCorrectHashCodeBehavior() {
            EmailParser.ParsedEmail email1 = EmailParser.sliceEmail("user@domain.com");
            EmailParser.ParsedEmail email2 = EmailParser.sliceEmail("user@domain.com");

            assertEquals(email1.hashCode(), email2.hashCode());
        }

        @Test
        @DisplayName("should have meaningful toString")
        void shouldHaveMeaningfulToString() {
            EmailParser.ParsedEmail email = EmailParser.sliceEmail("user@domain.com");

            String toString = email.toString();
            assertTrue(toString.contains("user"));
            assertTrue(toString.contains("domain.com"));
        }
    }
}
