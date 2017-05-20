package kth.ID2212.ac.client.screens;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import kth.ID2212.ac.client.State;
import kth.ID2212.ac.common.AnticheatServer;
import kth.ID2212.ac.common.entities.UserData;
import kth.ID2212.ac.common.exceptions.BannedClientException;
import kth.ID2212.ac.common.exceptions.InvalidCredentialsException;

import java.rmi.RemoteException;

/**
 * Screen is responsible for Login window.
 */
public class LoginScreen extends BaseScreen {
    protected Button btnLogin, btnRegistration;
    protected TextField inName;
    protected PasswordField inPass;

    public LoginScreen() {
        name = Screen.LOGIN;
        sceneFile = "login.fxml";
    }

    @Override
    public Scene load(Parent root) {
        State state = screenManager.getState();
        Scene scene = new Scene(root, 472, 200);
        btnLogin = (Button) scene.lookup("#btnLogin");
        btnRegistration = (Button) scene.lookup("#btnRegister");

        inName = (TextField) scene.lookup("#inName");
        inPass = (PasswordField) scene.lookup("#inPass");

        btnRegistration.setOnAction((ActionEvent event) -> {
            display(Screen.REGISTRATION);
        });

        btnLogin.setOnAction((ActionEvent event) -> {
            if (inName.getLength() < 3) {
                displayError("Validation error", "Username length must be at least 3 characters");
                return;
            }
            if (inPass.getLength() < 4) {
                displayError("Validation error", "Password length must be at least 4 characters");
                return;
            }
            AnticheatServer server = state.getServer();
            Task task = new Task<UserData>() {
                @Override
                public UserData call() throws RemoteException, InvalidCredentialsException, BannedClientException {
                    return server.connect(inName.getText(), inPass.getText(), state.getClient());
                }
            };
            task.setOnSucceeded(evt -> {
                UserData userData = (UserData) task.getValue();
                state.setUserData(userData);
                display(Screen.MAIN);
            });
            task.setOnFailed(evt -> displayError("Login error", task.getException().getMessage()));
            new Thread(task).start();
        });
        return scene;
    }
}
