package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.MetrologistViewService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class MetrologistViewController {

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
    private TableView<TestBench> TV_BENCH;
    @FXML
    private TableColumn<TestBench, String> TC_B_ID;
    @FXML
    private TableColumn<TestBench, String> TC_B_NAME;
    @FXML
    private TableColumn<TestBench, Date> TC_B_MAINTENANCE;
    @FXML
    private TableColumn<TestBench, Date> TC_B_CALIBRATION;

    @FXML
    private Label LF_MESSAGE;

    @FXML
    private ListView<Metrology> LV_POPUP;

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    private final MetrologistViewService metrologistViewService = MetrologistViewService.getInstance();

    public void initialize() {
        getMetrologies();
        getTestBenches();
        getPopUpMetrologyMaintenance();
    }

    @FXML
    private void getMetrologies() {

        List<Metrology> metrologyData = metrologistViewService.getAllMetrologies();
        StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");
        TV_METROLOGY.getItems().clear();

        TC_M_ID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().metrologyId()));
        TC_M_INVENTORY.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().iavInventory()));
        TC_M_MANUFACTURER.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().manufacturer()));
        TC_M_TYPE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().type()));
        TC_M_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_M_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
        TC_M_CALIBRATION.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_M_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
        TV_METROLOGY.getItems().addAll(metrologyData);
    }

    @FXML
    private void getTestBenches() {

        List<TestBench> testBenchData = metrologistViewService.getAllTestBenches();
        StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");


        TC_B_ID.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().benchId()));
        TC_B_NAME.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().name()));
        TC_B_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_B_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
        TC_B_CALIBRATION.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_B_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
        TV_BENCH.getItems().addAll(testBenchData);
    }

    @FXML
    public void getPopUpMetrologyMaintenance() {

        List<Metrology> metrologyByMaintenanceDue = metrologistViewService.getMetrologyByMaintenanceDue();
        if (metrologyByMaintenanceDue != null) {
            LV_POPUP.getItems().addAll(metrologyByMaintenanceDue);
        }

    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }

    @FXML
    public void onClick_PB_DELETE_METROLOGY() {
        metrologistViewService.deleteMetrology(TV_METROLOGY.getSelectionModel().getSelectedItem().metrologyId());
        getMetrologies();
    }

    @FXML
    public void onClick_PB_BENCH_DETAIL(ActionEvent event) throws IOException {
        sceneSwitchService.switchToTestBenchDetailView(event);
    }

    @FXML
    public void onClick_PB_METROLOGY_DETAIL(ActionEvent event) throws IOException {
        sceneSwitchService.switchToMetrologyDetailView(event);
    }
}
