package kth.ID2212.ac.common.exceptions;

/**
 * Informs that user does not exists or password is invalid
 */
public class InvalidCredentialsException extends ApplicationException {
    public InvalidCredentialsException() {
        super();
    }
    public InvalidCredentialsException(String message) {
        super(message);
    }
    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}