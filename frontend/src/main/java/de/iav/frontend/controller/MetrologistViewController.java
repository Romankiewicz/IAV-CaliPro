package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MetrologistViewController {

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToStartView(event);
    }
}
