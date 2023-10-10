package de.iav.frontend.controller;

import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.security.UserRole;
import de.iav.frontend.service.LoginViewService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    private final LoginViewService loginViewService = LoginViewService.getInstance();

    @FXML
    private Button PB_RETURN;
    @FXML
    private Button PB_LOGIN;
    @FXML
    private TextField TF_USERNAME;
    @FXML
    private PasswordField PF_PASSWORD;
    private String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");

    public void initialize() {

    }

    @FXML
    public void onClick_PB_RETURN(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToStartView(event);
    }

    @FXML
    public void onClick_PB_LOGIN_SwitchToNextView(ActionEvent event) throws IOException {
        if (isEveryTextFieldValid()) {
            String username = TF_USERNAME.getText();
            String password = PF_PASSWORD.getText();
            boolean result = AuthenticationService.getInstance().login(username, password);

            if (result && !AuthenticationService.getInstance().equals("anonymousUser")) {

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/api/metrologist/username" ))
                        .header("Accept", "application/json")
                        .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                        .build();

                var response = AuthenticationService
                        .getInstance()
                        .getAuthenticationClient()
                        .sendAsync(request, HttpResponse.BodyHandlers.ofString());
                int statusCode = response.join().statusCode();
                String body = response.join().body();

                if (statusCode == 202 && body.length() > 0) {
                    SceneSwitchService.getInstance().switchToMetrologistView(event);
                } else {
                    System.out.println("LOGIN FAILED!!!" +"\n" + statusCode + "\n" + body);
                }
            }
        }
    }

    private boolean isEveryTextFieldValid() {
        if (TF_USERNAME.getText() == null || TF_USERNAME.getText().isEmpty()) {
            System.out.println("Bitte Benutzernamen eingeben");
            return false;
        } else if (PF_PASSWORD.getText() == null || PF_PASSWORD.getText().isEmpty()) {
            System.out.println("Ohne Passwort geht hier garnichts...");
            return false;
        } else {
            return true;
        }
    }

}
