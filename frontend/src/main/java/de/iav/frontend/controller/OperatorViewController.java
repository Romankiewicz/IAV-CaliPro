package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TestBenchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OperatorViewController {

    @FXML
    private TableView<TestBench> TV_Bench;
    @FXML
    private TableColumn<TestBench, String> TC_B_NAME;
    @FXML
    private TableColumn<TestBench, Date> TC_B_MAINTENANCE;
    @FXML
    private TableColumn<TestBench, Date> TC_B_CALIBRATION;
    @FXML
    private TableColumn<TestBench, List<Metrology>> TC_B_METROLOGY;
    @FXML
    private TableColumn<Metrology, String> TC_M_MANUFACTRUER;
    @FXML
    private TableColumn<Metrology, String> TC_M_TYPE;
    @FXML
    private TableColumn<Metrology, Date> TC_M_MAINTENANCE;
    @FXML
    private TableColumn<Metrology, Date> TC_M_CALIBRATION;

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final TestBenchService testBenchService = TestBenchService.getInstance();


    public void initialize() {

    }



    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
}
