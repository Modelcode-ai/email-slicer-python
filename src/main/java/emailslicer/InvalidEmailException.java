package emailslicer;

/**
 * Thrown when an email address cannot be parsed because it is null, empty,
 * or does not contain a valid {@code @} separator.
 */
public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String message) {
        super(message);
    }
}
