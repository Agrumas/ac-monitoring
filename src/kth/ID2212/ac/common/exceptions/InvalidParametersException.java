package kth.ID2212.ac.common.exceptions;

/**
 * Informs that user's input is invalid.
 */
public class InvalidParametersException extends ApplicationException {
    public InvalidParametersException() {
        super();
    }
    public InvalidParametersException(String message) {
        super(message);
    }
    public InvalidParametersException(String message, Throwable cause) {
        super(message, cause);
    }
}