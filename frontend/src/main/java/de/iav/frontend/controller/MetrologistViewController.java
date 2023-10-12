package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.io.IOException;

public class MetrologistViewController {

    @FXML
    private ListView<Metrology> LV_METROLOGY;
    @FXML
    private ListView<TestBench> LV_BENCH;
    @FXML
    private Label LF_MESSAGE;
    @FXML
    private Button PB_HOME;
    @FXML
    private Button PB_ADD_METROLOGY;
    @FXML
    private Button PB_DETAIL;
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();


    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
}
