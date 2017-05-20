package kth.ID2212.ac.client;

import javafx.application.Application;
import javafx.stage.Stage;
import kth.ID2212.ac.client.screens.LoginScreen;
import kth.ID2212.ac.client.screens.MainScreen;
import kth.ID2212.ac.client.screens.RegistrationScreen;
import kth.ID2212.ac.client.screens.Screen;
import kth.ID2212.ac.common.AnticheatServer;

import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Anticheat client entry point.
 * On startup client tries to locate Anticheat server in JNDI/RMI Registry Service Provider.
 */
public class AntiCheatApp extends Application {
    protected AnticheatServer server;
    protected Client client;
    protected State state;
    protected ScreenManager screenManager;
    protected Screen screen;

    public AntiCheatApp() {

        try {
            client = new Client();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            server = (AnticheatServer) Naming.lookup(AnticheatServer.NAME);
        } catch (Exception e) {
            System.out.println("Starting ClientApp failed: " + e.getMessage());
            System.exit(0);
        }

        state = new State(server, client);
        System.out.println("Connected to AC AnticheatServer");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        screenManager = new ScreenManager(primaryStage, state);
        screenManager.register(new LoginScreen());
        screenManager.register(new RegistrationScreen());
        screenManager.register(new MainScreen());
        screenManager.display(LoginScreen.LOGIN);
        client.setScreenManager(screenManager);
    }

    @Override
    public void stop() {
        if (state.getUserData() != null) {
            try {
                server.disconnect(state.getUserData().getName(), client);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        System.exit(0);
    }
}
