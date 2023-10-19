package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.Operator;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.MetrologyService;
import de.iav.frontend.service.OperatorViewService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TestBenchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestBenchDetailViewController {

    @FXML
    private ComboBox<Operator> CB_OPERATOR;
    @FXML
    private ComboBox<TestBench> CB_TESTBENCH;
    @FXML
    private DatePicker DP_DATE;
    private Date selectedDate;
    private Operator selectedOperator;
    private TestBench selectedTestBench;
    private Metrology selectedMetrology;


    @FXML
    private TableView<TestBench> TV_BENCH;
    @FXML
    private TableColumn<TestBench, String> TC_B_NAME;
    @FXML
    private TableColumn<TestBench, Date> TC_B_MAINTENANCE;
    @FXML
    private TableColumn<TestBench, Date> TC_B_CALIBRATION;
    @FXML
    private TableColumn<TestBench, List<Operator>> TC_B_OPERATOR;
    @FXML
    private TableColumn<TestBench, List<Metrology>> TC_B_METROLOGY;

    @FXML
    private TableView<Metrology> TV_METROLOGY;
    @FXML
    private TableColumn<Metrology,String> TC_M_INVENTORY;
    @FXML
    private TableColumn<Metrology, String> TC_M_MANUFACTURER;
    @FXML
    private TableColumn<Metrology, String> TC_M_TYPE;
    @FXML
    private TableColumn<Metrology, Date> TC_M_MAINTENANCE;
    @FXML
    private TableColumn<Metrology, Date> TC_M_CALIBRATION;

    @FXML
    private final MetrologyService metrologyService = MetrologyService.getInstance();
    @FXML
    private final TestBenchService testBenchService = TestBenchService.getInstance();
    @FXML
    private final OperatorViewService operatorViewService = OperatorViewService.getInstance();
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();

    private List<Operator> getAllOperators() {
        return operatorViewService.getAllOperators();
    }

    private List<TestBench> getAllTestBenches() {
        return testBenchService.getAllTestBenches();
    }

    private List<Metrology> getAllMetrologies() {
        return metrologyService.getAllMetrologies();
    }


    public void initialize() {
        List<Metrology> allMetrologies = getAllMetrologies();
        List<TestBench> allTestBenches = getAllTestBenches();
        List<Operator> allOperators = getAllOperators();

        CB_OPERATOR.getItems().clear();
        CB_OPERATOR.getItems().addAll(allOperators);

        updateOperatorComboBox();

        CB_TESTBENCH.getItems().clear();
        CB_TESTBENCH.getItems().addAll(allTestBenches);

        updateTestBenchComboBox();
    }

    @FXML
    public void updateOperatorComboBox() {

        List<Operator> allOperators = getAllOperators();
        CB_OPERATOR.getItems().clear();
        CB_OPERATOR.getItems().addAll(allOperators);

        CB_OPERATOR.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedOperator = operatorViewService.getOperatorById(newValue.operatorId());
            }
        });
    }

    @FXML
    public void updateTestBenchComboBox() {

        List<TestBench> allTestBenches = getAllTestBenches();
        CB_TESTBENCH.getItems().clear();
        CB_TESTBENCH.getItems().addAll(allTestBenches);

        CB_TESTBENCH.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedTestBench = testBenchService.getTestBenchById(newValue.benchId());
                updateTestBenchTableView();
            }
        });
    }

    @FXML
    public void updateTestBenchTableView() {

        if (selectedTestBench != null) {
            List<TestBench> testBench = new ArrayList<>();
            testBench.add(selectedTestBench);

            StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");
            TV_BENCH.getItems().clear();

            TC_B_NAME.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().name()));
            TC_B_MAINTENANCE.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
            TC_B_MAINTENANCE.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
            TC_B_CALIBRATION.setCellFactory(TextFieldTableCell.forTableColumn(dateConverter));
            TC_B_CALIBRATION.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
            TC_B_METROLOGY.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().metrology()));
            TC_B_OPERATOR.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().operator()));
            TV_BENCH.getItems().addAll(testBench);
            TV_BENCH.setVisible(true);
        } else {
            TV_BENCH.setVisible(false);
        }
    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        sceneSwitchService.switchToStartView(event);
    }
    @FXML
    public void onClick_PB_CLOSE(ActionEvent event) throws IOException {
        sceneSwitchService.switchToMetrologistView(event);
    }
}
