package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class StartViewController {

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();


    @FXML
    public void onClick_PB_REGISTRATION(ActionEvent event) throws IOException {
        sceneSwitchService.switchToRegistrationView(event);

    }

    @FXML
    public void onClick_PB_LOGIN(ActionEvent event) throws IOException {
        sceneSwitchService.switchToLoginView(event);
    }
}
