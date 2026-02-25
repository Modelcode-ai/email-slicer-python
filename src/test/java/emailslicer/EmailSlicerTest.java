package emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for {@link EmailSlicer#parse(String)}.
 */
class EmailSlicerTest {

    // -----------------------------------------------------------------------
    // Valid email parsing
    // -----------------------------------------------------------------------

    @Test
    void parseValidEmail() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("avimax37@gmail.com");
        assertNotNull(parts);
        assertEquals("avimax37", parts.getUsername());
        assertEquals("gmail.com", parts.getDomain());
    }

    @Test
    void parseValidEmailWithSubdomain() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user@mail.example.com");
        assertEquals("user", parts.getUsername());
        assertEquals("mail.example.com", parts.getDomain());
    }

    @Test
    void parseValidEmailSimple() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("hello@world");
        assertEquals("hello", parts.getUsername());
        assertEquals("world", parts.getDomain());
    }

    // -----------------------------------------------------------------------
    // Invalid email handling
    // -----------------------------------------------------------------------

    @Test
    void parseNullThrowsException() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.parse(null));
    }

    @Test
    void parseEmptyStringThrowsException() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.parse(""));
    }

    @Test
    void parseWhitespaceOnlyThrowsException() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.parse("   "));
    }

    @Test
    void parseNoAtSymbolThrowsException() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.parse("invalidemail"));
    }

    @Test
    void parseTabsAndSpacesOnlyThrowsException() {
        assertThrows(InvalidEmailException.class, () -> EmailSlicer.parse("\t  \t"));
    }

    // -----------------------------------------------------------------------
    // Edge cases
    // -----------------------------------------------------------------------

    @Test
    void parseMultipleAtSplitsOnFirst() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("name@sub@domain.com");
        assertEquals("name", parts.getUsername());
        assertEquals("sub@domain.com", parts.getDomain());
    }

    @Test
    void parseLeadingAndTrailingWhitespace() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("  user@example.com  ");
        assertEquals("user", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }

    @Test
    void parseAtStartEmptyUsername() {
        // Preserves Python parity: @domain.com is valid with empty username
        EmailSlicer.EmailParts parts = EmailSlicer.parse("@domain.com");
        assertEquals("", parts.getUsername());
        assertEquals("domain.com", parts.getDomain());
    }

    @Test
    void parseAtEndEmptyDomain() {
        // Preserves Python parity: user@ is valid with empty domain
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user@");
        assertEquals("user", parts.getUsername());
        assertEquals("", parts.getDomain());
    }

    @Test
    void parseSingleAtChar() {
        // Edge case: just "@" — contains @, so valid per Python parity
        EmailSlicer.EmailParts parts = EmailSlicer.parse("@");
        assertEquals("", parts.getUsername());
        assertEquals("", parts.getDomain());
    }

    @Test
    void parseEmailWithPlusTag() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("user+tag@example.com");
        assertEquals("user+tag", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }

    @Test
    void parseEmailWithDotsInUsername() {
        EmailSlicer.EmailParts parts = EmailSlicer.parse("first.last@example.com");
        assertEquals("first.last", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }
}
