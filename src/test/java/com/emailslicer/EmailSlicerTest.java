package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 *
 * <p>Covers valid emails, invalid inputs, whitespace handling, and edge cases
 * to ensure behavioral parity with the original Python emailSlicer.py script.</p>
 */
class EmailSlicerTest {

    @Nested
    @DisplayName("Valid email addresses")
    class ValidEmails {

        @ParameterizedTest(name = "\"{0}\" -> username=\"{1}\", domain=\"{2}\"")
        @CsvSource({
                "avimax37@gmail.com,       avimax37,       gmail.com",
                "user@mail.example.com,    user,           mail.example.com",
                "john.doe@company.org,     john.doe,       company.org",
                "test+label@domain.io,     test+label,     domain.io"
        })
        @DisplayName("Standard email addresses are parsed correctly")
        void standardEmails(String input, String expectedUsername, String expectedDomain) {
            ParsedEmail result = EmailSlicer.parse(input);
            assertEquals(expectedUsername, result.username());
            assertEquals(expectedDomain, result.domain());
        }

        @Test
        @DisplayName("Email with leading and trailing whitespace is trimmed and parsed")
        void emailWithWhitespace() {
            ParsedEmail result = EmailSlicer.parse("  user@example.com  ");
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("Email with tabs and spaces is trimmed correctly")
        void emailWithTabsAndSpaces() {
            ParsedEmail result = EmailSlicer.parse("\t user@example.com \t");
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }
    }

    @Nested
    @DisplayName("Invalid email addresses")
    class InvalidEmails {

        @ParameterizedTest(name = "\"{0}\" is invalid (no @ symbol)")
        @ValueSource(strings = {"plainaddress", "user.domain.com", "nodomain"})
        @DisplayName("Emails without @ throw IllegalArgumentException")
        void emailsWithoutAt(String input) {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.parse(input)
            );
            assertNotNull(ex.getMessage());
        }

        @Test
        @DisplayName("Null input throws IllegalArgumentException")
        void nullInput() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(null));
        }

        @ParameterizedTest(name = "Blank input \"{0}\" throws IllegalArgumentException")
        @ValueSource(strings = {"", "   ", "\t", "\n"})
        @DisplayName("Empty or blank strings throw IllegalArgumentException")
        void emptyOrBlankInput(String input) {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.parse(input));
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("@ at the start: username is empty, domain is 'domain.com'")
        void atAtStart() {
            ParsedEmail result = EmailSlicer.parse("@domain.com");
            assertEquals("", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("@ at the end: username is 'user', domain is empty")
        void atAtEnd() {
            ParsedEmail result = EmailSlicer.parse("user@");
            assertEquals("user", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("Multiple @ characters: splits on the first @")
        void multipleAtCharacters() {
            ParsedEmail result = EmailSlicer.parse("user@@example.com");
            assertEquals("user", result.username());
            assertEquals("@example.com", result.domain());
        }

        @Test
        @DisplayName("Only @ character: username and domain are both empty")
        void onlyAtCharacter() {
            ParsedEmail result = EmailSlicer.parse("@");
            assertEquals("", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("Email with multiple @ signs (complex)")
        void complexMultipleAt() {
            ParsedEmail result = EmailSlicer.parse("a@b@c@d");
            assertEquals("a", result.username());
            assertEquals("b@c@d", result.domain());
        }
    }
}
