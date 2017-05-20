package kth.ID2212.ac.client.screens;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import kth.ID2212.ac.client.State;
import kth.ID2212.ac.common.entities.UserData;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Main application screen which is visible after user connects.
 * Screen displays username and some details, like count of warnings, last login date.
 *
 * Screen information is taken from context, it can be updated using {@link #refresh()}
 */
public class MainScreen extends BaseScreen {
    protected Button btnExit;
    protected Text txtUsername, txtDate, txtWarnings;

    public MainScreen() {
        name = Screen.MAIN;
        sceneFile = "main.fxml";
    }

    @Override
    public Scene load(Parent root) {
        Scene scene = new Scene(root, 368, 182);
        btnExit = (Button) scene.lookup("#btnExit");
        txtUsername = (Text) scene.lookup("#txtUsername");
        txtDate = (Text) scene.lookup("#txtDate");
        txtWarnings = (Text) scene.lookup("#txtWarnings");

        btnExit.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });
        refreshInfo();

        return scene;
    }

    protected void refreshInfo() {
        State state = screenManager.getState();
        UserData data = state.getUserData();
        if (data == null) {
            return;
        }
        Date date = new Date(data.getLastLogin() * 1000);
        txtUsername.setText(data.getName());
        txtDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date));
        txtWarnings.setText(data.getWarnings() + "");
    }

    @Override
    public void refresh() {
        refreshInfo();
    }
}
