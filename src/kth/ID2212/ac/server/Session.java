package kth.ID2212.ac.server;

import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.common.entities.ProcessList;
import kth.ID2212.ac.common.exceptions.UserIsOfflineException;
import kth.ID2212.ac.entities.User;

import java.rmi.RemoteException;

/**
 * User's connection representation on server side.
 * It has information about user {@link User} and remote control object {@link AnticheatClient}.
 */
public class Session {
    public String username;
    public User user;
    public AnticheatClient client;
    private SessionsBean handler;

    public Session(SessionsBean handler, User user, AnticheatClient client) {
        this.user = user;
        this.client = client;
        this.handler = handler;
        this.username = user.getName();
    }
}
