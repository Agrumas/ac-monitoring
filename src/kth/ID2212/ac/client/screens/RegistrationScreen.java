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
import kth.ID2212.ac.common.exceptions.DuplicateNameException;
import kth.ID2212.ac.common.exceptions.InvalidParametersException;

import java.rmi.RemoteException;

/**
 * Registration screen, it also performs some validation of user's input.
 */
public class RegistrationScreen extends BaseScreen {

    protected Button btnRegister;
    protected TextField inName;
    protected PasswordField inPass, inPassRepeat;

    public RegistrationScreen() {
        name = Screen.REGISTRATION;
        sceneFile = "registration.fxml";
    }

    @Override
    public Scene load(Parent root) {
        State state = screenManager.getState();
        Scene scene = new Scene(root, 472, 223);
        btnRegister = (Button) scene.lookup("#btnRegister");

        inName = (TextField) scene.lookup("#inName");
        inPass = (PasswordField) scene.lookup("#inPass");
        inPassRepeat = (PasswordField) scene.lookup("#inPassRepeat");

        btnRegister.setOnAction((ActionEvent event) -> {
            if (inName.getLength() < 3) {
                displayError("Validation error", "Username length must be at least 3 characters");
                return;
            }
            if (inPass.getLength() < 4) {
                displayError("Validation error", "Password length must be at least 4 characters");
                return;
            }
            if (!inPass.getText().equals(inPassRepeat.getText())) {
                displayError("Validation error", "Passwords do not match");
                return;
            }
            AnticheatServer server = state.getServer();
            Task task = new Task<Boolean>() {
                @Override public Boolean call() throws InvalidParametersException, RemoteException, DuplicateNameException {
                    server.register(inName.getText(), inPass.getText());
                    return null;
                }
            };
            task.setOnSucceeded(evt -> {
                displayInfo("Registration succeeded!", "Now you can login with credentials passed before.");
                display(Screen.LOGIN);
            });
            task.setOnFailed(evt -> displayError("Registration error", task.getException().getMessage()));
            new Thread(task).start();
        });
        return scene;
    }
}
