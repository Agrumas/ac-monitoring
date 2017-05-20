package kth.ID2212.ac.server;

import kth.ID2212.ac.api.util.PasswordUtils;
import kth.ID2212.ac.common.exceptions.DuplicateNameException;
import kth.ID2212.ac.common.exceptions.InvalidCredentialsException;
import kth.ID2212.ac.common.exceptions.InvalidParametersException;
import kth.ID2212.ac.common.exceptions.UserNotFoundException;
import kth.ID2212.ac.entities.User;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Repository of user. Class is responsible for JPA actions for registration, login and fetching.
 */
@Stateless(name = "UsersEJB")
public class UsersBean {
    @PersistenceContext
    private EntityManager em;

    public UsersBean() {
    }

    public User getById(int id) {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_ID, User.class);
        query.setParameter("id", id);
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            throw new UserNotFoundException("User not found");
        }
        return users.get(0);
    }

    /**
     * Fetches all users
     * @return List of Users
     */
    public List<User> getAll() {
        TypedQuery<User> query = em.createNamedQuery(User.FIND_ALL, User.class);
        return query.getResultList();
    }

    public User login(String username, String password) throws InvalidCredentialsException {
        if (password.length() < 4) {
            throw new InvalidCredentialsException("Password is too short.");
        }

        if (username.length() < 4) {
            throw new InvalidCredentialsException("Username is too short.");
        }

        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN_PASSWORD, User.class);
        query.setParameter("login", username);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        List<User> users = query.getResultList();
        if (users.size() == 0) {
            throw new InvalidCredentialsException("User not found or password is incorrect");
        }
        User user = users.get(0);
        em.refresh(user);
        user.afterLogin();
        em.flush();
        return user;
    }

    /**
     * Register user with provided credentials
     * @param username Unique username
     * @param password password
     * @return a new instance of User
     * @throws DuplicateNameException when username is taken
     * @throws InvalidParametersException when user's input is invalid, for example noo username/ password
     */
    public User register(String username, String password) throws DuplicateNameException, InvalidParametersException {
        if (password.length() < 4) {
            throw new InvalidParametersException("Password is too short.");
        }

        if (username.length() < 3) {
            throw new InvalidParametersException("Username is too short.");
        }

        TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_LOGIN, User.class);
        query.setParameter("login", username);
        if (query.getResultList().size() != 0) {
            throw new DuplicateNameException("Username is taken.");
        }

        User user = new User();
        user.setName(username);
        user.setPassword(PasswordUtils.digestPassword(password));
        em.persist(user);
        em.flush();
        return user;
    }
}
