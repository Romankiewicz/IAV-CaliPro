package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.Operator;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.OperatorViewService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TestBenchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OperatorViewController {

    @FXML
    private TableView<TestBench> TV_BENCH;
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
    private final OperatorViewService operatorViewService = OperatorViewService.getInstance();
    @FXML
    private final TestBenchService testBenchService = TestBenchService.getInstance();


    public void initialize() {
        getTestBenchOfLoginOperator();
    }

    @FXML
    private void getTestBenchOfLoginOperator() {

        Operator loginOperator = operatorViewService.getLoginOperator();

        List<TestBench> testBenchData = loginOperator.testBench();
        StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");


        TC_B_NAME.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().name()));
        TC_B_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_B_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
        TC_B_CALIBRATION.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_B_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
        TC_M_MANUFACTRUER.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().manufacturer()));
        TC_M_TYPE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().type()));
        TC_B_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_B_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
        TC_M_CALIBRATION.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
        TC_M_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
        TV_BENCH.getItems().addAll(testBenchData);
    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
}
