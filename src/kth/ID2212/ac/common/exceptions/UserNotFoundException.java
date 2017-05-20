package kth.ID2212.ac.common.exceptions;

/**
 * Informs that selected user does not exist.
 */
public class UserNotFoundException extends ApplicationException {
    public UserNotFoundException() {
        super();
    }
    public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatus() {
        return 404;
    }
}