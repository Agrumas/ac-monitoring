package kth.ID2212.ac.client.screens;

import javafx.scene.control.Alert;

/**
 * Interface of Alerts displaying
 */
public interface AlertsSupport {
    /**
     * Opens general Alert
     *
     * @param title   Title
     * @param header  Header
     * @param content Content
     * @param type    Type of alert
     * @return Alert
     */
    Alert displayAlert(String title, String header, String content, Alert.AlertType type);

    /**
     * Displays an informational alert
     *
     * @param title   Title
     * @param content Content
     * @return Alert
     */
    Alert displayInfo(String title, String content);

    /**
     * Displays an error alert
     *
     * @param title   Title
     * @param header  Header
     * @param content Content
     * @return Alert
     */
    Alert displayError(String title, String header, String content);

    /**
     * Displays an error alert
     *
     * @param title   Title
     * @param content Content
     * @return Alert
     */
    Alert displayError(String title, String content);

    /**
     * Displays a warning alert
     *
     * @param title   Title
     * @param content Content
     * @return Alert
     */
    Alert displayWarning(String title, String content);
}
