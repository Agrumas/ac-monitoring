package kth.ID2212.ac.common.exceptions;

/**
 * Informs that selected user is not connected.
 */
public class UserIsOfflineException extends ApplicationException {
    public UserIsOfflineException() {
        super();
    }
    public UserIsOfflineException(String message) {
        super(message);
    }
    public UserIsOfflineException(String message, Throwable cause) {
        super(message, cause);
    }

    public int getStatus() {
        return 404;
    }
}