package com.emailslicer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailSlicerTest {

    @Nested
    @DisplayName("Valid emails")
    class ValidEmails {

        @Test
        @DisplayName("simple email is sliced into username and domain")
        void simpleEmail() {
            EmailSliceResult result = EmailSlicer.slice("user@example.com");
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("email with dotted username")
        void dottedUsername() {
            EmailSliceResult result = EmailSlicer.slice("first.last@domain.co.uk");
            assertEquals("first.last", result.username());
            assertEquals("domain.co.uk", result.domain());
        }

        @Test
        @DisplayName("email with plus addressing")
        void plusAddressing() {
            EmailSliceResult result = EmailSlicer.slice("user+tag@example.com");
            assertEquals("user+tag", result.username());
            assertEquals("example.com", result.domain());
        }
    }

    @Nested
    @DisplayName("Whitespace handling")
    class WhitespaceHandling {

        @Test
        @DisplayName("leading and trailing spaces are trimmed")
        void leadingTrailingSpaces() {
            EmailSliceResult result = EmailSlicer.slice("  user@example.com  ");
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }

        @Test
        @DisplayName("tabs and mixed whitespace are trimmed")
        void tabsAndWhitespace() {
            EmailSliceResult result = EmailSlicer.slice("\t user@example.com \t");
            assertEquals("user", result.username());
            assertEquals("example.com", result.domain());
        }
    }

    @Nested
    @DisplayName("Invalid emails — missing @")
    class MissingAt {

        @Test
        @DisplayName("no @ symbol throws IllegalArgumentException")
        void noAtSymbol() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice("invalidemail"));
        }

        @Test
        @DisplayName("plain word throws IllegalArgumentException")
        void plainWord() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice("nodomain"));
        }
    }

    @Nested
    @DisplayName("Invalid emails — empty or blank")
    class EmptyOrBlank {

        @Test
        @DisplayName("null throws IllegalArgumentException")
        void nullInput() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice(null));
        }

        @Test
        @DisplayName("empty string throws IllegalArgumentException")
        void emptyString() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice(""));
        }

        @Test
        @DisplayName("blank string throws IllegalArgumentException")
        void blankString() {
            assertThrows(IllegalArgumentException.class, () -> EmailSlicer.slice("   "));
        }
    }

    @Nested
    @DisplayName("Edge cases")
    class EdgeCases {

        @Test
        @DisplayName("@ only — returns empty username and domain")
        void atOnly() {
            EmailSliceResult result = EmailSlicer.slice("@");
            assertEquals("", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("@ at start — empty username")
        void atStart() {
            EmailSliceResult result = EmailSlicer.slice("@domain.com");
            assertEquals("", result.username());
            assertEquals("domain.com", result.domain());
        }

        @Test
        @DisplayName("@ at end — empty domain")
        void atEnd() {
            EmailSliceResult result = EmailSlicer.slice("user@");
            assertEquals("user", result.username());
            assertEquals("", result.domain());
        }

        @Test
        @DisplayName("multiple @ symbols — splits on first @")
        void multipleAt() {
            EmailSliceResult result = EmailSlicer.slice("user@@domain.com");
            assertEquals("user", result.username());
            assertEquals("@domain.com", result.domain());
        }

        @Test
        @DisplayName("a@b@c — splits on first @")
        void threePartsAt() {
            EmailSliceResult result = EmailSlicer.slice("a@b@c");
            assertEquals("a", result.username());
            assertEquals("b@c", result.domain());
        }
    }

    @Nested
    @DisplayName("Error message content")
    class ErrorMessages {

        @Test
        @DisplayName("error message is user-friendly")
        void errorMessageContent() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> EmailSlicer.slice("nope")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }
    }
}
