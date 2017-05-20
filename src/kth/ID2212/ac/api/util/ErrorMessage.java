package kth.ID2212.ac.api.util;

import kth.ID2212.ac.common.exceptions.ApplicationException;

/**
 * Exception object used to represent exception in JSON.
 */
public class ErrorMessage {

    int status;

    String message;

    public ErrorMessage(ApplicationException ex) {
        this.status = ex.getStatus();
        this.message = ex.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
