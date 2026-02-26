package emailslicer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link EmailSlicer}.
 * <p>
 * Covers valid email parsing, invalid email detection, edge cases around {@code @},
 * and whitespace handling — all matching the behavior of the original Python script.
 */
class EmailSlicerTest {

    private EmailSlicer slicer;

    @BeforeEach
    void setUp() {
        slicer = new EmailSlicer();
    }

    @Nested
    @DisplayName("Valid email parsing")
    class ValidEmails {

        @Test
        @DisplayName("parses standard email into username and domain")
        void parsesStandardEmail() {
            EmailParts parts = slicer.slice("avimax37@gmail.com");
            assertEquals("avimax37", parts.username());
            assertEquals("gmail.com", parts.domain());
        }

        @Test
        @DisplayName("parses email with subdomain")
        void parsesEmailWithSubdomain() {
            EmailParts parts = slicer.slice("user@mail.example.co.uk");
            assertEquals("user", parts.username());
            assertEquals("mail.example.co.uk", parts.domain());
        }

        @Test
        @DisplayName("parses email with dots in username")
        void parsesEmailWithDotsInUsername() {
            EmailParts parts = slicer.slice("first.last@example.com");
            assertEquals("first.last", parts.username());
            assertEquals("example.com", parts.domain());
        }
    }

    @Nested
    @DisplayName("Invalid email (no @)")
    class InvalidEmails {

        @Test
        @DisplayName("throws for plain string without @")
        void throwsForPlainString() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> slicer.slice("plainaddress")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }

        @Test
        @DisplayName("throws for string with dots but no @")
        void throwsForDottedStringWithoutAt() {
            IllegalArgumentException ex = assertThrows(
                    IllegalArgumentException.class,
                    () -> slicer.slice("user.domain.com")
            );
            assertEquals("Please enter a valid Email Id.", ex.getMessage());
        }

        @Test
        @DisplayName("throws for empty string")
        void throwsForEmptyString() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> slicer.slice("")
            );
        }

        @Test
        @DisplayName("throws for whitespace-only string")
        void throwsForWhitespaceOnly() {
            assertThrows(
                    IllegalArgumentException.class,
                    () -> slicer.slice("   ")
            );
        }
    }

    @Nested
    @DisplayName("Edge cases around @")
    class EdgeCases {

        @Test
        @DisplayName("handles empty username (@domain.com)")
        void handlesEmptyUsername() {
            EmailParts parts = slicer.slice("@domain.com");
            assertEquals("", parts.username());
            assertEquals("domain.com", parts.domain());
        }

        @Test
        @DisplayName("handles empty domain (user@)")
        void handlesEmptyDomain() {
            EmailParts parts = slicer.slice("user@");
            assertEquals("user", parts.username());
            assertEquals("", parts.domain());
        }

        @Test
        @DisplayName("splits on first @ when multiple @ present")
        void splitsOnFirstAt() {
            EmailParts parts = slicer.slice("user@sub@domain.com");
            assertEquals("user", parts.username());
            assertEquals("sub@domain.com", parts.domain());
        }

        @Test
        @DisplayName("handles just @ character")
        void handlesJustAtSign() {
            EmailParts parts = slicer.slice("@");
            assertEquals("", parts.username());
            assertEquals("", parts.domain());
        }
    }

    @Nested
    @DisplayName("Whitespace handling")
    class WhitespaceHandling {

        @Test
        @DisplayName("trims leading whitespace")
        void trimsLeadingWhitespace() {
            EmailParts parts = slicer.slice("  avimax37@gmail.com");
            assertEquals("avimax37", parts.username());
            assertEquals("gmail.com", parts.domain());
        }

        @Test
        @DisplayName("trims trailing whitespace")
        void trimsTrailingWhitespace() {
            EmailParts parts = slicer.slice("avimax37@gmail.com  ");
            assertEquals("avimax37", parts.username());
            assertEquals("gmail.com", parts.domain());
        }

        @Test
        @DisplayName("trims both leading and trailing whitespace")
        void trimsBothSides() {
            EmailParts parts = slicer.slice("  avimax37@gmail.com  ");
            assertEquals("avimax37", parts.username());
            assertEquals("gmail.com", parts.domain());
        }
    }
}
