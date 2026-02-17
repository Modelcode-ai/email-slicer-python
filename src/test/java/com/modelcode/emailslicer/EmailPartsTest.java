package com.modelcode.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EmailPartsTest {

    @Test
    void gettersReturnConstructorValues() {
        EmailParts parts = new EmailParts("alice", "example.com");
        assertEquals("alice", parts.getUsername());
        assertEquals("example.com", parts.getDomain());
    }

    @Test
    void toStringContainsUsernameAndDomain() {
        EmailParts parts = new EmailParts("bob", "domain.org");
        String str = parts.toString();
        assertEquals("EmailParts{username='bob', domain='domain.org'}", str);
    }
}
