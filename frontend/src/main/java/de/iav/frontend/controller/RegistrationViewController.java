package de.iav.frontend.controller;

import de.iav.frontend.model.MetrologistDTO;
import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.security.UserRole;
import de.iav.frontend.service.RegistrationViewService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RegistrationViewController {

    @FXML
    private ChoiceBox<String> CB_ROLE;
    @FXML
    private TextField TF_USERNAME;
    @FXML
    private TextField TF_FIRSTNAME;
    @FXML
    private TextField TF_LASTNAME;
    @FXML
    private TextField TF_EMAIL;
    @FXML
    private PasswordField PF_PASSWORD;
    @FXML
    private Label LF_ERROR;
    private UserRole selectedRole;

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    @FXML
    private final RegistrationViewService registrationViewService = RegistrationViewService.getInstance();


    public void initialize() {

        selectedRole = UserRole.ADMIN;

        CB_ROLE.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.equals("Als Prüfstandsfahrer registrieren")) {
                        this.selectedRole = UserRole.OPERATOR;
                    } else if (newValue.equals("Als Messtechniker registrieren")) {
                        this.selectedRole = UserRole.METROLOGIST;
                    }
                }
        );
    }


    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }

    @FXML
    public void onClick_PB_REGISTER_SwitchToNextView(ActionEvent event) throws IOException {
        if(isEveryTextFieldValid()) {
            String userName = TF_USERNAME.getText();
            String firstName = TF_FIRSTNAME.getText();
            String lastName = TF_LASTNAME.getText();
            String email = TF_EMAIL.getText();
            String password = PF_PASSWORD.getText();

            if (selectedRole.equals(UserRole.METROLOGIST)) {
                if (AuthenticationService.getInstance().addMetrologist(userName, password)) {
                    MetrologistDTO newMetrologist = new MetrologistDTO(userName, password, firstName, lastName, email);

                    boolean result = authenticationService.login(userName, password);

                    if (result && !authenticationService.getUsername().equals("anonymousUser")) {
                        registrationViewService.addMetrologist(newMetrologist, authenticationService.getSessionId());
                    } else {
                        LF_ERROR.setText("Registrierung fehlgeschlagen!!!");
                    }
                    sceneSwitchService.switchToStartView(event);
                } else {
                    LF_ERROR.setText(authenticationService.getErrorMassage());
                }
            }
        }
    }
    private boolean isEveryTextFieldValid() {
        if (TF_USERNAME.getText() == null || TF_USERNAME.getText().isEmpty()) {
            LF_ERROR.setText("Bitte Benutzernamen eingeben");
            return false;
        } else if (TF_FIRSTNAME.getText() == null || TF_FIRSTNAME.getText().isEmpty()) {
            LF_ERROR.setText("Btte Vornamen eingeben");
            return false;
        } else if (TF_LASTNAME.getText() == null || TF_LASTNAME.getText().isEmpty()) {
            LF_ERROR.setText("Bitte Namen eingeben");
            return false;
        } else if (TF_EMAIL.getText() == null || TF_EMAIL.getText().isEmpty()) {
            LF_ERROR.setText("Bitte E-Mailadresse eingeben");
            return false;
        } else if (PF_PASSWORD.getText() == null || PF_PASSWORD.getText().isEmpty()) {
            LF_ERROR.setText("Ohne Passwort geht hier garnichts...");
            return false;
        } else {
            LF_ERROR.setText("");
            return true;
        }
    }
}
