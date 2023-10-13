package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.MetrologistViewService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MetrologistViewController {

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
    private TableColumn<Metrology, Date> TC_M_MAINTENANCE;
    @FXML
    private TableColumn<Metrology, Date> TC_M_CALIBRATION;

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
        StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");

        TC_M_ID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().metrologyId()));
        TC_M_INVENTORY.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().iavInventory()));
        TC_M_MANUFACTURER.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().manufacturer()));
        TC_M_TYPE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().type()));
        TC_M_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_M_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
        TC_M_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_M_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
        TV_METROLOGY.getItems().addAll(metrologyData);
    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
}
