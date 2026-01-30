package com.modelcode.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmailParserTest {

    private final EmailParser parser = new EmailParser();

    @Test
    void parsesSimpleValidEmail() {
        EmailComponents result = parser.parse("avimax37@gmail.com");
        assertEquals("avimax37", result.username());
        assertEquals("gmail.com", result.domain());
    }
}
