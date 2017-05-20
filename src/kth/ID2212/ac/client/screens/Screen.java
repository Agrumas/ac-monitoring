package kth.ID2212.ac.client.screens;

import javafx.scene.Parent;
import javafx.scene.Scene;
import kth.ID2212.ac.client.ScreenManager;

import java.net.URL;

/**
 * Screen is responsible for GUI logic of particular application screen.
 */
public interface Screen {
    String LOGIN = "login";
    String REGISTRATION = "registration";
    String MAIN = "main";

    /**
     * The name of the screen. Name is used to display a particular screen.
     * @return name of the screen
     */
    String getName();

    /**
     * Location of the form.
     * @return Location of the form
     */
    URL getSceneURL();

    /**
     * Creates scene and binds logic.
     *
     * @param root form of the screen
     * @return scene
     */
    Scene load(Parent root);

    /**
     * Attaches screen manager to screen
     * @param screenManager Screen manager to control screens.
     */
    void setScreenManager(ScreenManager screenManager);

    /**
     * Reload data from context to screen.
     * Not all screens use this.
     */
    void refresh();
}
