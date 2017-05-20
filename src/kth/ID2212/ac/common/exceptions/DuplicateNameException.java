package kth.ID2212.ac.common.exceptions;

/**
 * Informs that another user is using specified name.
 */
public class DuplicateNameException extends ApplicationException {
    public DuplicateNameException() {
        super();
    }
    public DuplicateNameException(String message) {
        super(message);
    }
    public DuplicateNameException(String message, Throwable cause) {
        super(message, cause);
    }
}