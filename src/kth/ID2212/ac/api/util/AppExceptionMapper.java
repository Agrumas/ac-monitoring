package kth.ID2212.ac.api.util;

import kth.ID2212.ac.common.exceptions.ApplicationException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Converts Application specific exceptions to JSON format when exception occurs.
 * All exceptions by default are displayed in HTML.
 */
public class AppExceptionMapper implements ExceptionMapper<ApplicationException> {

    public Response toResponse(ApplicationException ex) {
        return Response.status(ex.getStatus())
                .entity(new ErrorMessage(ex))
                .type(MediaType.APPLICATION_JSON).
                        build();
    }

}