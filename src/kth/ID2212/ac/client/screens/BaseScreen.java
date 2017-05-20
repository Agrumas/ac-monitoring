package kth.ID2212.ac.client.screens;

import javafx.scene.control.Alert;
import kth.ID2212.ac.client.ScreenManager;

import java.net.URL;

/**
 * Abstract class to provide basic functionality of screen.
 */
public abstract class BaseScreen implements Screen, AlertsSupport {
    protected ScreenManager screenManager;
    public String name;
    public String sceneFile;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public URL getSceneURL() {
        return getClass().getResource(sceneFile);
    }

    @Override
    public void setScreenManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }

    public void display(String name) {
        screenManager.display(name);
    }

    @Override
    public void refresh() {
    }

    public Alert displayAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();

        return alert;
    }

    public Alert displayInfo(String title, String content) {
        return displayAlert(title, null, content, Alert.AlertType.INFORMATION);
    }

    public Alert displayWarning(String title, String content) {
        return displayAlert(title, null, content, Alert.AlertType.WARNING);
    }

    public Alert displayError(String title, String header, String content) {
        return displayAlert(title, header, content, Alert.AlertType.ERROR);
    }

    public Alert displayError(String title, String content) {
        return displayError(title, null, content);
    }
}
