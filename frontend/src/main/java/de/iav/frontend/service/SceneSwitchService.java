package de.iav.frontend.service;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitchService {

    private static SceneSwitchService instance;

    public static synchronized SceneSwitchService getInstance() {
        if (instance == null) {
            instance = new SceneSwitchService();
        }
        return instance;
    }

    public void switchToStartView(ActionEvent event) throws IOException {

        FXMLLoader loaderRegistrationView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_StartView.fxml"));

        Scene sceneRegView = new Scene(loaderRegistrationView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToRegistrationView(ActionEvent event) throws IOException {

        FXMLLoader loaderRegistrationView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_RegistrationView.fxml"));

        Scene sceneRegView = new Scene(loaderRegistrationView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToLoginView(ActionEvent event) throws IOException {

        FXMLLoader loaderLoginView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_LoginView.fxml"));

        Scene sceneRegView = new Scene(loaderLoginView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }
}
