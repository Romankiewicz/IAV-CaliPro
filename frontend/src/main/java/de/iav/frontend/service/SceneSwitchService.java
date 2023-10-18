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

    public void switchToMetrologistView(ActionEvent event) throws IOException {

        FXMLLoader loaderMetrologistView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_MetrologistView.fxml"));

        Scene sceneRegView = new Scene(loaderMetrologistView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToOperatorView(ActionEvent event) throws IOException {

        FXMLLoader loaderOperatorView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_OperatorView.fxml"));

        Scene sceneRegView = new Scene(loaderOperatorView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToTestBenchDetailView(ActionEvent event) throws IOException {

        FXMLLoader loaderTestBenchDetailView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_TestBenchDetailView.fxml"));

        Scene sceneRegView = new Scene(loaderTestBenchDetailView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToMetrologyDetailView(ActionEvent event) throws IOException {

        FXMLLoader loaderMetrologyDetailView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_MetrologyDetailView.fxml"));

        Scene sceneRegView = new Scene(loaderMetrologyDetailView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

    public void switchToAddMetrologyView(ActionEvent event) throws IOException {

        FXMLLoader loaderAddMetrologyView = new FXMLLoader(getClass()
                .getResource("/de/iav/frontend/fxml/CaliPro_AddMetrologyView.fxml"));

        Scene sceneRegView = new Scene(loaderAddMetrologyView.load());

        Stage stage = (Stage) (((Node) event.getSource()).getScene().getWindow());

        stage.setScene(sceneRegView);
        stage.show();
    }

}
