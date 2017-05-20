package kth.ID2212.ac.common.exceptions;

import java.io.Serializable;

/**
 * Base exception of application domain exceptions.
 */
@javax.ejb.ApplicationException
public abstract class ApplicationException extends RuntimeException implements Serializable {

    public ApplicationException() {
        super();
    }

    public ApplicationException(String message) {
        super(message);
    }

    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Status of HTTP status code for current exception.
     * @return HTTP status code
     */
    public int getStatus() {
        return 400;
    }
}