package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserTest {

    private EmailParser parser;

    @BeforeEach
    void setUp() {
        parser = new EmailParser();
    }

    @Nested
    @DisplayName("Valid email parsing")
    class ValidEmailTests {

        @Test
        @DisplayName("parses standard email address")
        void parsesStandardEmail() {
            Optional<EmailParts> result = parser.parse("user@domain.com");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().getUsername());
            assertEquals("domain.com", result.get().getDomain());
        }

        @Test
        @DisplayName("parses email from Python example")
        void parsesPythonExampleEmail() {
            Optional<EmailParts> result = parser.parse("avimax37@gmail.com");

            assertTrue(result.isPresent());
            assertEquals("avimax37", result.get().getUsername());
            assertEquals("gmail.com", result.get().getDomain());
        }

        @Test
        @DisplayName("trims leading whitespace")
        void trimsLeadingWhitespace() {
            Optional<EmailParts> result = parser.parse("  user@domain.com");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().getUsername());
            assertEquals("domain.com", result.get().getDomain());
        }

        @Test
        @DisplayName("trims trailing whitespace")
        void trimsTrailingWhitespace() {
            Optional<EmailParts> result = parser.parse("user@domain.com  ");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().getUsername());
            assertEquals("domain.com", result.get().getDomain());
        }

        @Test
        @DisplayName("trims surrounding whitespace")
        void trimsSurroundingWhitespace() {
            Optional<EmailParts> result = parser.parse("  user@domain.com  ");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().getUsername());
            assertEquals("domain.com", result.get().getDomain());
        }

        @Test
        @DisplayName("handles email with dots in username")
        void handlesDotsInUsername() {
            Optional<EmailParts> result = parser.parse("first.last@example.com");

            assertTrue(result.isPresent());
            assertEquals("first.last", result.get().getUsername());
            assertEquals("example.com", result.get().getDomain());
        }

        @Test
        @DisplayName("handles email with subdomain")
        void handlesSubdomain() {
            Optional<EmailParts> result = parser.parse("user@mail.example.com");

            assertTrue(result.isPresent());
            assertEquals("user", result.get().getUsername());
            assertEquals("mail.example.com", result.get().getDomain());
        }
    }

    @Nested
    @DisplayName("Invalid email parsing")
    class InvalidEmailTests {

        @Test
        @DisplayName("rejects email without @ symbol")
        void rejectsEmailWithoutAtSymbol() {
            Optional<EmailParts> result = parser.parse("invalidemail");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects email with empty username (leading @)")
        void rejectsEmptyUsername() {
            Optional<EmailParts> result = parser.parse("@domain.com");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects email with empty domain (trailing @)")
        void rejectsEmptyDomain() {
            Optional<EmailParts> result = parser.parse("user@");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects email with multiple @ symbols")
        void rejectsMultipleAtSymbols() {
            Optional<EmailParts> result = parser.parse("user@@domain.com");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects email with @ in username and domain")
        void rejectsAtInBothParts() {
            Optional<EmailParts> result = parser.parse("user@name@domain.com");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects empty string")
        void rejectsEmptyString() {
            Optional<EmailParts> result = parser.parse("");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects whitespace only")
        void rejectsWhitespaceOnly() {
            Optional<EmailParts> result = parser.parse("   ");

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects null input")
        void rejectsNullInput() {
            Optional<EmailParts> result = parser.parse(null);

            assertTrue(result.isEmpty());
        }

        @Test
        @DisplayName("rejects just @ symbol")
        void rejectsJustAtSymbol() {
            Optional<EmailParts> result = parser.parse("@");

            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("Parameterized invalid email tests")
    class ParameterizedInvalidTests {

        @ParameterizedTest
        @NullSource
        @ValueSource(strings = {"", "   ", "invalidemail", "@domain.com", "user@", "@@", "@", "user@@domain.com"})
        @DisplayName("rejects invalid emails")
        void rejectsInvalidEmails(String input) {
            Optional<EmailParts> result = parser.parse(input);

            assertTrue(result.isEmpty(), "Expected empty result for input: " + input);
        }
    }
}
