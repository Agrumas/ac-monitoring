package kth.ID2212.ac.client;

import javafx.application.Platform;
import kth.ID2212.ac.client.screens.AlertsSupport;
import kth.ID2212.ac.client.screens.Screen;
import kth.ID2212.ac.common.AnticheatClient;
import kth.ID2212.ac.common.entities.UserData;
import kth.ID2212.ac.common.entities.ProcessList;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Anticheat client
 */
public class Client extends UnicastRemoteObject implements AnticheatClient {

    protected transient ScreenManager screenManager;

    protected Client() throws RemoteException {
        super();
    }

    @Override
    public byte[] screen() throws RemoteException {
        try {
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", bytes);
            return bytes.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ProcessList getProcesses() throws RemoteException {
        ListProcesses processes = new ListProcesses();
        return processes.get();
    }

    @Override
    public void onWarning(String reason) throws RemoteException {
        AlertsSupport screen = (AlertsSupport) screenManager.getScreen();
        Platform.runLater(() -> screen.displayWarning("Warning received", reason));
    }

    @Override
    public void onBan(String reason) throws RemoteException {
        AlertsSupport screen = (AlertsSupport) screenManager.getScreen();
        Platform.runLater(() -> {
            screen.displayError("You have been banned", reason);
            Platform.exit();
        });
    }

    @Override
    public void onUserUpdate(UserData data) throws RemoteException {
        State state = screenManager.getState();
        state.setUserData(data);
        Screen screen = screenManager.getScreen();
        Platform.runLater(() -> screen.refresh());
    }

    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
