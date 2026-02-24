package com.emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 * <p>
 * Covers happy-path parsing, whitespace trimming, invalid-input rejection,
 * multiple-{@code @} behaviour, and edge cases documented in the migration spec.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    // ------------------------------------------------------------------ //
    //  Happy-path tests
    // ------------------------------------------------------------------ //

    @Nested
    @DisplayName("Happy path - valid email addresses")
    class HappyPath {

        @Test
        @DisplayName("Typical email: avimax37@gmail.com")
        void typicalEmail() {
            EmailSlicer.Result result = slicer.parse("avimax37@gmail.com");

            assertEquals("avimax37", result.getUsername());
            assertEquals("gmail.com", result.getDomain());
        }

        @Test
        @DisplayName("Email with subdomain: user@mail.example.co.uk")
        void emailWithSubdomain() {
            EmailSlicer.Result result = slicer.parse("user@mail.example.co.uk");

            assertEquals("user", result.getUsername());
            assertEquals("mail.example.co.uk", result.getDomain());
        }

        @Test
        @DisplayName("Email with dots in username: first.last@example.com")
        void emailWithDotsInUsername() {
            EmailSlicer.Result result = slicer.parse("first.last@example.com");

            assertEquals("first.last", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        @DisplayName("Email with plus addressing: user+tag@example.com")
        void emailWithPlusAddressing() {
            EmailSlicer.Result result = slicer.parse("user+tag@example.com");

            assertEquals("user+tag", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }
    }

    // ------------------------------------------------------------------ //
    //  Whitespace trimming tests
    // ------------------------------------------------------------------ //

    @Nested
    @DisplayName("Whitespace trimming")
    class WhitespaceTrimming {

        @Test
        @DisplayName("Leading whitespace is trimmed")
        void leadingWhitespace() {
            EmailSlicer.Result result = slicer.parse("  user@example.com");

            assertEquals("user", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        @DisplayName("Trailing whitespace is trimmed")
        void trailingWhitespace() {
            EmailSlicer.Result result = slicer.parse("user@example.com  ");

            assertEquals("user", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        @DisplayName("Both leading and trailing whitespace is trimmed")
        void bothSidesWhitespace() {
            EmailSlicer.Result result = slicer.parse("  user@example.com  ");

            assertEquals("user", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        @DisplayName("Tabs and mixed whitespace are trimmed")
        void tabsAndMixedWhitespace() {
            EmailSlicer.Result result = slicer.parse("\t user@example.com \t");

            assertEquals("user", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }
    }

    // ------------------------------------------------------------------ //
    //  Invalid input tests
    // ------------------------------------------------------------------ //

    @Nested
    @DisplayName("Invalid input - should throw IllegalArgumentException")
    class InvalidInput {

        @Test
        @DisplayName("Null input throws")
        void nullInput() {
            assertThrows(IllegalArgumentException.class, () -> slicer.parse(null));
        }

        @Test
        @DisplayName("Empty string throws")
        void emptyString() {
            assertThrows(IllegalArgumentException.class, () -> slicer.parse(""));
        }

        @Test
        @DisplayName("Whitespace-only string throws")
        void whitespaceOnly() {
            assertThrows(IllegalArgumentException.class, () -> slicer.parse("   "));
        }

        @Test
        @DisplayName("No '@' character throws")
        void noAtSign() {
            assertThrows(IllegalArgumentException.class, () -> slicer.parse("userexample.com"));
        }

        @Test
        @DisplayName("Plain text without '@' throws")
        void plainText() {
            assertThrows(IllegalArgumentException.class, () -> slicer.parse("just-a-string"));
        }
    }

    // ------------------------------------------------------------------ //
    //  Multiple '@' characters
    // ------------------------------------------------------------------ //

    @Nested
    @DisplayName("Multiple '@' characters - split on first")
    class MultipleAtSigns {

        @Test
        @DisplayName("Two '@' signs: user@sub@example.com")
        void twoAtSigns() {
            EmailSlicer.Result result = slicer.parse("user@sub@example.com");

            assertEquals("user", result.getUsername());
            assertEquals("sub@example.com", result.getDomain());
        }

        @Test
        @DisplayName("Three '@' signs: a@b@c@d")
        void threeAtSigns() {
            EmailSlicer.Result result = slicer.parse("a@b@c@d");

            assertEquals("a", result.getUsername());
            assertEquals("b@c@d", result.getDomain());
        }
    }

    // ------------------------------------------------------------------ //
    //  Edge cases - '@' only or missing components
    // ------------------------------------------------------------------ //

    @Nested
    @DisplayName("Edge cases - '@' with empty components")
    class EdgeCases {

        @Test
        @DisplayName("'@' only yields empty username and domain")
        void atSignOnly() {
            // Matches Python behavior: '@' contains '@', so it's accepted.
            // Username and domain are both empty strings.
            EmailSlicer.Result result = slicer.parse("@");

            assertEquals("", result.getUsername());
            assertEquals("", result.getDomain());
        }

        @Test
        @DisplayName("'@example.com' yields empty username")
        void missingUsername() {
            // Matches Python behavior: accepted with empty username.
            EmailSlicer.Result result = slicer.parse("@example.com");

            assertEquals("", result.getUsername());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        @DisplayName("'user@' yields empty domain")
        void missingDomain() {
            // Matches Python behavior: accepted with empty domain.
            EmailSlicer.Result result = slicer.parse("user@");

            assertEquals("user", result.getUsername());
            assertEquals("", result.getDomain());
        }
    }
}
