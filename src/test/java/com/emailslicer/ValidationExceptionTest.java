package com.emailslicer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ValidationException class.
 */
class ValidationExceptionTest {

    @Test
    void constructorWithMessage_shouldCreateExceptionWithMessage() {
        String message = "Invalid email format";
        ValidationException exception = new ValidationException(message);

        assertEquals(message, exception.getMessage());
    }

    @Test
    void constructorWithMessageAndCause_shouldCreateExceptionWithMessageAndCause() {
        String message = "Validation failed";
        Throwable cause = new IllegalArgumentException("Root cause");
        ValidationException exception = new ValidationException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }

    @Test
    void exceptionCanBeThrown() {
        String message = "Email must not be empty";

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            throw new ValidationException(message);
        });

        assertEquals(message, exception.getMessage());
    }

    @Test
    void exceptionCanBeCaught() {
        String message = "Email domain must contain a dot";
        boolean caught = false;
        try {
            throw new ValidationException(message);
        } catch (ValidationException e) {
            assertEquals(message, e.getMessage());
            caught = true;
        }
        assertTrue(caught, "Exception should have been thrown and caught");
    }

    @Test
    void exceptionIsRuntimeException() {
        ValidationException exception = new ValidationException("test");
        assertTrue(exception instanceof RuntimeException);
    }

    @Test
    void exceptionMessageIsPreserved() {
        String originalMessage = "Email format is invalid";
        ValidationException exception = new ValidationException(originalMessage);

        String retrievedMessage = exception.getMessage();

        assertEquals(originalMessage, retrievedMessage);
    }

    @Test
    void nullMessageIsAllowed() {
        ValidationException exception = new ValidationException(null);
        assertNull(exception.getMessage());
    }

    @Test
    void emptyMessageIsAllowed() {
        String emptyMessage = "";
        ValidationException exception = new ValidationException(emptyMessage);
        assertEquals(emptyMessage, exception.getMessage());
    }
}
