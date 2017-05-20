package kth.ID2212.ac.server;

import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.entities.User;

import javax.ejb.Singleton;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * EJB responsible for tracking active sessions with clients.
 */
@Singleton(name = "SessionsEJB")
public class SessionsBean {

    @Inject
    protected SyncContext context;

    HashMap<String, Session> list;

    public SessionsBean() {
        this.list = new HashMap<>();
    }

    public Session get(String username) {
        return list.get(username);
    }

    public Session get(int id) {
        return list.values().stream()
                .filter(x -> x.user.getId() == id)
                .findFirst().orElse(null);
    }

    public boolean exists(String username) {
        return list.containsKey(username);
    }

    public synchronized Session register(User user, AnticheatClient client) {
        Session u = new Session(this, user, client);
        list.put(user.getName(), u);
        context.send("logged-in:" + user.getName());
        return u;
    }

    public Collection<Session> getList() {
        return list.values();
    }

    public List<String> getConnectedNames() {
        return new ArrayList<>(list.keySet());
    }

    public synchronized Session disconnect(String username) {
        Session session = list.remove(username);
        if (session != null) {
            context.send("logout:" + username);
        }
        return session;
    }
}
