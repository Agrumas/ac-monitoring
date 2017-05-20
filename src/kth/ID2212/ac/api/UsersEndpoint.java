package kth.ID2212.ac.api;

import kth.ID2212.ac.api.filter.JWTTokenNeeded;
import kth.ID2212.ac.api.forms.PunishmentForm;
import kth.ID2212.ac.api.util.SuccessResponse;
import kth.ID2212.ac.common.entities.ProcessList;
import kth.ID2212.ac.entities.User;
import kth.ID2212.ac.server.ClientControlBean;
import kth.ID2212.ac.server.SessionsBean;
import kth.ID2212.ac.server.UsersBean;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This API endpoint defines actions of User resource
 */
@ApplicationScoped()
@Path("/users")
public class UsersEndpoint {

    @Context
    private UriInfo uriInfo;

    @PersistenceContext
    private EntityManager em;

    @EJB(beanName = "SessionsEJB")
    private transient SessionsBean sessions;

    @EJB(beanName = "UsersEJB")
    private UsersBean usersBean;

    @EJB(beanName = "ClientControlEJB")
    private ClientControlBean clientControlBean;

    public UsersEndpoint() {
    }

    /**
     * Fetches list of all Users
     *
     * @return list of users
     */
    @GET
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public List<User> list() {
        return usersBean.getAll();
    }

    /**
     * Makes screenshot of user and returns it.
     *
     * @param id user id
     * @return screenshot
     */
    @GET
    @Path("/{id}/screen")
    @JWTTokenNeeded
    @Produces({"image/png"})
    public Response getScreen(@PathParam("id") int id) {
        byte[] imageData = clientControlBean.makeScreenshot(id);
        return Response.ok(new ByteArrayInputStream(imageData)).build();
    }


    /**
     * Endpoint returns all details of requested user (password is excluded)
     *
     * @param id user id
     * @return Details of user
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @JWTTokenNeeded
    public User getUser(@PathParam("id") int id) {
        return usersBean.getById(id);
    }

    /**
     * Fetches process list with windows titles of user.
     *
     * @param id user id
     * @return Process list
     */
    @GET
    @Path("/{id}/processes")
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public ProcessList getProcesses(@PathParam("id") int id) {
        return clientControlBean.fetchProcesses(id);
    }

    /**
     * Blocks user from using anticheat
     *
     * @param id   user id
     * @param form reason why user is blocked
     * @return confirmation if operation succeeded
     */
    @POST
    @Path("/{id}/ban")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response banUser(@PathParam("id") int id, PunishmentForm form) {
        clientControlBean.banUser(id, form.reason);
        return SuccessResponse.build();
    }

    /**
     * Warn the user
     *
     * @param id   user id
     * @param form reason why user is warned
     * @return confirmation if operation succeeded
     */
    @POST
    @Path("/{id}/warn")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response warnUser(@PathParam("id") int id, PunishmentForm form) {
        clientControlBean.warnUser(id, form.reason);
        return SuccessResponse.build();
    }

    /**
     * Unblocks the user.
     *
     * @param id user id
     * @return confirmation if operation succeeded
     */
    @POST
    @Path("/{id}/unban")
    @JWTTokenNeeded
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response unbanUser(@PathParam("id") int id) {
        clientControlBean.unBanUser(id);
        return SuccessResponse.build();
    }

    class OnlineUser {
        public int id;
        public String name;

        public OnlineUser(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    /**
     * Serves name and id of connected users.
     *
     * @return list of connected users
     */
    @Path("online")
    @GET
    @JWTTokenNeeded
    @Produces({MediaType.APPLICATION_JSON})
    public List<OnlineUser> onlineUsers() {
        return sessions.getList().stream().map(session -> {
            User u = session.user;
            return new OnlineUser(u.getId(), u.getName());
        }).collect(Collectors.toList());
    }

}
