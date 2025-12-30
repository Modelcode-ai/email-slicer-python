package com.emailslicer;

/**
 * Unchecked exception thrown when email validation fails.
 * Provides descriptive error messages for various validation failure scenarios.
 */
public class InvalidEmailException extends RuntimeException {
  /**
   * Constructs a new InvalidEmailException with the specified detail message.
   *
   * @param message the detail message explaining why validation failed
   */
  public InvalidEmailException(String message) {
    super(message);
  }
}
