package kth.ID2212.ac.api.util;

import javax.ws.rs.core.Response;

/**
 * Response object which represents successful operation
 */
public class SuccessResponse {
    public boolean success = true;

    /**
     * Generates new Success Response object
     * @return SuccessResponse object
     */
    public static Response build(){
        return Response.ok(new SuccessResponse()).build();
    }
}
