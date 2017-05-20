package kth.ID2212.ac.common.exceptions;

/**
 * Informs that user is blocked from the system.
 */
public class BannedClientException extends ApplicationException {
    public BannedClientException() {
        super();
    }
    public BannedClientException(String message) {
        super(message);
    }
    public BannedClientException(String message, Throwable cause) {
        super(message, cause);
    }
}