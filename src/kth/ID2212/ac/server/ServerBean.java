package kth.ID2212.ac.server;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Anticheat server EJB which registers in Locate Registry to be discoverable.
 */
@Singleton(name = "ServerEJB")
@Startup
public class ServerBean {
    protected Server server;

    @EJB(beanName = "SessionsEJB")
    private SessionsBean sessions;

    @EJB(beanName = "UsersEJB")
    private UsersBean usersBean;

    @EJB(beanName = "ClientControlEJB")
    private ClientControlBean clientControlBean;

    @PersistenceContext
    private EntityManager em;

    public ServerBean() {
    }

    @PostConstruct
    public void init() {
        try {
            server = new Server(usersBean, sessions, clientControlBean);

            try {
                LocateRegistry.getRegistry(1099).list();
            } catch (RemoteException e) {
                LocateRegistry.createRegistry(1099);
            }
            Naming.rebind(Server.NAME, server);
            System.out.println(Server.NAME + " is ready. " + server);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
