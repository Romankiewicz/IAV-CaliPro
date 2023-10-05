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
import javafx.scene.input.KeyEvent;

import java.io.IOException;

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
    private UserRole selectedRole;

    public void initialize() {

    }

    @FXML
    public void onClick_PB_RETURN(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToStartView(event);
    }

    @FXML
    public void onClick_PB_LOGIN_SwitchToNextView(ActionEvent event) throws IOException {
        if (isEveryTextFieldValid()) {
            String userName = TF_USERNAME.getText();
            String password = PF_PASSWORD.getText();
            boolean result = AuthenticationService.getInstance().login(userName, password);

            if (result && !AuthenticationService.getInstance().equals("anonymousUser")) {
                if
            }
        }
    }

    private boolean isEveryTextFieldValid() {
    }
}
