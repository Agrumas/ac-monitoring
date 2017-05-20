package kth.ID2212.ac.server;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;

/**
 * Service tracks Web Socket connections and broadcasts application events to them.
 */
@ApplicationScoped
public class SyncContext {
    private Set<Session> sessions;

    @PostConstruct
    public void init() {
        sessions = new HashSet<>();
    }

    public synchronized void add(Session session) {
        if (sessions.contains(session)) {
            return;
        }
        sessions.add(session);
    }

    public synchronized void remove(Session session) {
        sessions.remove(session);
    }

    /**
     * Broadcasts message to all Web Socket connections
     * @param message text to be broadcasted
     */
    public void send(String message) {
        for (Session userSession : sessions) {
            if (userSession.isOpen()) {
                userSession.getAsyncRemote().sendText(message);
            }
        }
    }

}