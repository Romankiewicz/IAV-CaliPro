package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.MetrologistViewService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MetrologistViewController {

    @FXML
    private ListView<Metrology> LV_METROLOGY;
    @FXML
    private ListView<TestBench> LV_BENCH;

    @FXML
    private TableView<Metrology> TV_METROLOGY;
    @FXML
    private TableColumn<Metrology, String> TC_M_ID;
    @FXML
    private TableColumn<Metrology, String> TC_M_INVENTORY;
    @FXML
    private TableColumn<Metrology, String> TC_M_MANUFACTURER;
    @FXML
    private TableColumn<Metrology, String> TC_M_TYPE;
    @FXML
    private TableColumn<Metrology, LocalDate> TC_M_MAINTENANCE;
    @FXML
    private TableColumn<Metrology, LocalDate> TC_M_CALIBRATION;

    @FXML
    private Label LF_MESSAGE;
    @FXML
    private Button PB_HOME;
    @FXML
    private Button PB_ADD_METROLOGY;
    @FXML
    private Button PB_METROLOGY_DETAIL;
    @FXML
    private Button PB_BENCH_DETAIL;
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final MetrologistViewService metrologistViewService = MetrologistViewService.getInstance();

    public void initialize() {

        List<Metrology> metrologyData = metrologistViewService.getAllMetrologies();
        LV_METROLOGY.getItems().addAll(metrologyData);

        TC_M_ID.setCellValueFactory(new PropertyValueFactory<>("metrologyId"));
        TC_M_INVENTORY.setCellValueFactory(new PropertyValueFactory<>("iavInventory"));
        TC_M_MANUFACTURER.setCellValueFactory(new PropertyValueFactory<>("manufacturer"));
        TC_M_TYPE.setCellValueFactory(new PropertyValueFactory<>("type"));
        TC_M_MAINTENANCE.setCellValueFactory(new PropertyValueFactory<>("maintenance"));
        TC_M_CALIBRATION.setCellValueFactory(new PropertyValueFactory<>("calibration"));
        TV_METROLOGY.getItems().addAll(metrologyData);
    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
}
