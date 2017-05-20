package kth.ID2212.ac.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import kth.ID2212.ac.client.screens.Screen;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

/**
 * Control of visible screen of application.
 * Application GUI consists of multiple screens and one shared state.
 *
 * @see Screen which is responsible for UI logic
 * @see State where context is saved, for example details of user
 */
public class ScreenManager {

    protected Stage primaryStage;
    protected State state;
    protected Screen screen;
    protected HashMap<String, Screen> screens;

    public ScreenManager(Stage primaryStage, State state) {
        this.state = state;
        this.primaryStage = primaryStage;
        screens = new HashMap<>();
    }

    /**
     * Registers the screen in the manager
     * @param screen Screen
     */
    public void register(Screen screen) {
        screen.setScreenManager(this);
        screens.put(screen.getName(), screen);
    }

    /**
     * Returns application context
     * @return application context
     */
    public State getState() {
        return state;
    }

    /**
     * Returns visible screen
     * @return visible screen
     */
    public Screen getScreen() {
        return screen;
    }

    /**
     * Activates Screen by name.
     * This is responsible for loading a UI and binding the logic.
     * @param name the name of the screen
     * @return status if screen is loaded
     */
    public boolean display(String name) {
        Screen screen = screens.get(name);
        if (screen == null) {
            return false;
        }
        Parent parent;
        try {
            parent = loadGUI(screen.getSceneURL());
        } catch (IOException e) {
            return false;
        }
        this.screen = screen;
        Scene scene = screen.load(parent);
        show(scene);
        return true;
    }

    protected void show(Scene scene) {
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    protected Parent loadGUI(URL url) throws IOException {
        return FXMLLoader.load(url);
    }
}
