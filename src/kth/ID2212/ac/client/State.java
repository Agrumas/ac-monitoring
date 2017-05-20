package kth.ID2212.ac.client;

import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.common.AnticheatServer;
import kth.ID2212.ac.common.entities.UserData;

/**
 * Application state.
 *
 */
public class State {

    protected AnticheatServer server;
    protected AnticheatClient client;
    protected UserData userData;

    public State(AnticheatServer server, AnticheatClient client) {
        this.server = server;
        this.client = client;
    }

    public AnticheatServer getServer() {
        return server;
    }

    public AnticheatClient getClient() {
        return client;
    }

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }
}
