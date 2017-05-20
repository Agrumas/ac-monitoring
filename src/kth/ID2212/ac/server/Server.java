package kth.ID2212.ac.server;

import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.common.AnticheatServer;
import kth.ID2212.ac.common.entities.UserData;
import kth.ID2212.ac.common.exceptions.BannedClientException;
import kth.ID2212.ac.common.exceptions.DuplicateNameException;
import kth.ID2212.ac.common.exceptions.InvalidCredentialsException;
import kth.ID2212.ac.common.exceptions.InvalidParametersException;
import kth.ID2212.ac.entities.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Remote control object for server for client usage.
 */
public class Server extends UnicastRemoteObject implements AnticheatServer {

    private transient UsersBean usersBean;
    private transient SessionsBean sessions;
    private transient ClientControlBean clientControlBean;

    protected Server(UsersBean usersBean, SessionsBean sessions, ClientControlBean clientControlBean) throws RemoteException {
        super();
        this.usersBean = usersBean;
        this.sessions = sessions;
        this.clientControlBean = clientControlBean;
    }

    @Override
    public boolean ping() throws RemoteException {
        return true;
    }

    /**
     * Creates a new user in AntiCheat system.
     *
     * @param username unique user name
     * @param password password
     * @return status of operation
     * @throws RemoteException            when network issues occurs
     * @throws DuplicateNameException     when username is taken
     * @throws InvalidParametersException when format of username or password is invalid
     */
    @Override
    public boolean register(String username, String password) throws RemoteException, DuplicateNameException, InvalidParametersException {
        usersBean.register(username, password);
        return true;
    }

    @Override
    public UserData connect(String username, String password, AnticheatClient client) throws RemoteException, InvalidCredentialsException, BannedClientException {
        User user = usersBean.login(username, password);
        if (user.isBanned()) {
            throw new BannedClientException("User is banned");
        }
        sessions.register(user, client);
        return user.extractData();
    }

    /**
     * Gracefully disconnects user
     *
     * @param username Username
     * @param client   remote control object of client
     * @throws RemoteException when network issues occurs
     */
    @Override
    public void disconnect(String username, AnticheatClient client) throws RemoteException {
        clientControlBean.disconnect(username);
    }


}
