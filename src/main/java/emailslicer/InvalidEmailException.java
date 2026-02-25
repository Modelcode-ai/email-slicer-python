package emailslicer;

/**
 * Thrown when an email address cannot be parsed because it is null, empty,
 * whitespace-only, or does not contain an '@' character.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
