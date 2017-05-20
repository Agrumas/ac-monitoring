package kth.ID2212.ac.server;

import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.common.entities.ProcessList;
import kth.ID2212.ac.common.exceptions.UserIsOfflineException;
import kth.ID2212.ac.entities.User;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.rmi.RemoteException;

/**
 * Client control bean contains server side implementation of anticheat actions.
 * Since there is no active monitoring of client connectivity, some king of status tracking is done here.
 *
 * @author Algirdas
 */
@Stateless(name = "ClientControlEJB")
public class ClientControlBean {
    @EJB(beanName = "SessionsEJB")
    private SessionsBean sessions;

    @EJB(beanName = "UsersEJB")
    private UsersBean usersBean;

    @PersistenceContext
    private EntityManager em;

    /**
     * Give warning to user.
     * User is banned when count of warnings reaches {@link kth.ID2212.ac.entities.User#MAX_WARNINGS}
     *
     * @param id     id of user to be warn
     * @param reason explanation why user is warned
     */
    public void warnUser(int id, String reason) {
        User user = usersBean.getById(id);
        user.setWarnings(user.getWarnings() + 1);
        em.flush();
        if (user.getWarnings() >= User.MAX_WARNINGS) {
            banUser(id, "Auto ban. Maximum number of warnings received (warnings: " + user.getWarnings() + ")");
            return;
        }
        Session session = sessions.get(user.getId());
        if (session == null) {
            return;
        }
        AnticheatClient client = session.client;
        try {
            client.onUserUpdate(user.extractData());
            client.onWarning(reason);
        } catch (RemoteException e) {
            disconnect(user);
            e.printStackTrace();
        }
    }

    /**
     * Bans the user from using AntiCheat.
     * If user is connected, client is disconnected after he receives the message.
     *
     * @param id     user id to be banned
     * @param reason explanation why user is banned
     */
    public void banUser(int id, String reason) {
        User user = usersBean.getById(id);
        user.setBanned(true);
        em.flush();
        Session session = sessions.get(user.getId());
        if (session == null) {
            return;
        }
        AnticheatClient client = session.client;
        try {
            client.onUserUpdate(user.extractData());
            client.onBan(reason);
        } catch (RemoteException e) {
            disconnect(user);
            e.printStackTrace();
        }
    }

    /**
     * Unblocks the user and sets count of warnings to zero.
     *
     * @param id id of user to unblock
     */
    public void unBanUser(int id) {
        User user = usersBean.getById(id);
        user.setWarnings(0);
        user.setBanned(false);
        em.flush();
    }

    /**
     * Fetches process list of client.
     * Works only for connected users. This information is not saved anywhere.
     *
     * @param id user id
     *
     * @return Process list contains a path of executable and all titles of windows created by that program.
     */
    public ProcessList fetchProcesses(int id) {
        Session session = sessions.get(id);
        if (session == null) {
            disconnect(id);
            throw new UserIsOfflineException();
        }
        AnticheatClient client = session.client;
        try {
            return client.getProcesses();
        } catch (RemoteException e) {
            disconnect(id);
            throw new UserIsOfflineException();
        }
    }

    /**
     * Captures the screen shot of the user's visible screen.
     *
     * Works only for connected users. Screen shots aren't saved.
     *
     * @param id user id
     *
     * @return screenshot
     */
    public byte[] makeScreenshot(int id) {
        Session session = sessions.get(id);
        if (session == null) {
            disconnect(id);
            throw new UserIsOfflineException();
        }
        AnticheatClient client = session.client;
        try {
            return client.screen();
        } catch (RemoteException e) {
            disconnect(id);
            throw new UserIsOfflineException();
        }
    }

    /**
     * Disconnects user from anticheat server.
     * @param username user name to disconnect
     */
    public void disconnect(String username) {
        Session session = sessions.get(username);
        if (session == null) {
            return;
        }
        disconnect(session.user);
    }

    private void disconnect(int id) {
        User user = usersBean.getById(id);
        sessions.disconnect(user.getName());
        user.setOnline(false);
        em.flush();
    }

    private void disconnect(User user) {
        disconnect(user.getId());
    }
}
