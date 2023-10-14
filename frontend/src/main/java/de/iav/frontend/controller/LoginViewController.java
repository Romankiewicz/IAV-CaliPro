package de.iav.frontend.controller;

import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginViewController {

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    @FXML
    private Button PB_HOME;
    @FXML
    private Button PB_LOGIN;
    @FXML
    private Label LF_ERROR;
    @FXML
    private TextField TF_USERNAME;
    @FXML
    private PasswordField PF_PASSWORD;
    @FXML
    private String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");

    @FXML
    protected void onClick_PB_LOGIN(ActionEvent event) throws IOException {
        login(event);
    }

    public void initialize() {

    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }

    @FXML
    public void login(ActionEvent event) throws IOException {
        if (isEveryTextFieldValid()) {
            String username = TF_USERNAME.getText();
            String password = PF_PASSWORD.getText();
            boolean result = authenticationService.login(username, password);

            if (result && !authenticationService.getUsername().equals("anonymousUser")) {


                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrologist/" + username))
                        .header("Accept", "application/json")
                        .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                        .build();

                var response = authenticationService
                        .getAuthenticationClient()
                        .sendAsync(request, HttpResponse.BodyHandlers.ofString());
                int statusCode = response.join().statusCode();
                String body = response.join().body();

                if (statusCode == 202 && !body.isEmpty()) {
                    sceneSwitchService.switchToMetrologistView(event);
                } else {
                    LF_ERROR.setText("LOGIN FAILED!!!" + "\n" + statusCode + "\n" + body);
                }
            }
        }
    }

    private boolean isEveryTextFieldValid() {
        if (TF_USERNAME.getText() == null || TF_USERNAME.getText().isEmpty()) {
            LF_ERROR.setText("Bitte Benutzernamen eingeben");
            return false;
        } else if (PF_PASSWORD.getText() == null || PF_PASSWORD.getText().isEmpty()) {
            LF_ERROR.setText("Ohne Passwort geht hier garnichts...");
            return false;
        } else {
            return true;
        }
    }

}
