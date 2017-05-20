package kth.ID2212.ac.api;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kth.ID2212.ac.api.forms.LoginForm;
import kth.ID2212.ac.api.util.KeyGenerator;
import kth.ID2212.ac.api.util.PasswordUtils;
import kth.ID2212.ac.entities.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;

/**
 * Authentication endpoint.
 */
@ApplicationScoped()
@Path("/auth")
public class AuthEndpoint {

    @Context
    private UriInfo uriInfo;

    @Inject
    private KeyGenerator keyGenerator;

    @PersistenceContext
    private EntityManager em;

    public AuthEndpoint() {
    }

    /**
     * Response with JWT
     */
    public class TokenResponse {
        public String token;

        public TokenResponse(String token) {
            this.token = token;
        }
    }

    /**
     * Tries to auth user with provided credentials.
     * If credentials are correct, JWT (Json Web Token) is returned.
     *
     * @param form user credentials
     *
     * @return JWT
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticateUser(LoginForm form) {

        try {
            authenticate(form.name, form.password);
            String token = issueToken(form.name);

            return Response.ok(new TokenResponse(token)).header(AUTHORIZATION, "Bearer " + token).build();
        } catch (Exception e) {
            return Response.status(UNAUTHORIZED).build();
        }
    }

    private void authenticate(String login, String password) throws Exception {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_ADMIN_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("login", login);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        User user = query.getSingleResult();

        if (user == null)
            throw new SecurityException("Invalid user/password");
    }

    private String issueToken(String login) {
        Key key = keyGenerator.generateKey();
        String jwtToken = Jwts.builder()
                .setSubject(login)
                .setIssuer(uriInfo.getAbsolutePath().toString())
                .setIssuedAt(new Date())
                .setExpiration(toDate(LocalDateTime.now().plusMinutes(15L)))
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
        return jwtToken;

    }

    private Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

}
