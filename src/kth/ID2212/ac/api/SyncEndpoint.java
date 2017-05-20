package kth.ID2212.ac.api;

import kth.ID2212.ac.server.SyncContext;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 * Web Socket endpoint to subscribe for users events.
 * For example connection/disconnection of user.
 */
@ServerEndpoint(value = "/sync")
public class SyncEndpoint {

    @Inject
    protected SyncContext context;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        context.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        context.remove(session);
    }

    @OnMessage
    public String onMessage(String message, Session session) {
        return message;
    }
}