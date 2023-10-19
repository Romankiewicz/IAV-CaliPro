package de.iav.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.Operator;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.security.AuthenticationService;
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
import java.time.LocalDate;
import java.time.ZoneId;
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


    @FXML
    private TableView<TestBench> TV_BENCH;
    @FXML
    private TableColumn<TestBench, String> TC_B_NAME;
    @FXML
    private TableColumn<TestBench, Date> TC_B_MAINTENANCE;
    @FXML
    private TableColumn<TestBench, Date> TC_B_CALIBRATION;
    @FXML
    private TableColumn<TestBench, String> TC_B_OPERATOR;
    @FXML
    private TableColumn<TestBench, String> TC_B_METROLOGY;

    @FXML
    private TableView<Metrology> TV_METROLOGY;
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
    private final MetrologyService metrologyService = MetrologyService.getInstance();
    @FXML
    private final TestBenchService testBenchService = TestBenchService.getInstance();
    @FXML
    private final OperatorViewService operatorViewService = OperatorViewService.getInstance();
    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    private List<Operator> getAllOperators() {

        return operatorViewService.getAllOperators();
    }

    private List<TestBench> getAllTestBenches() {

        return testBenchService.getAllTestBenches();
    }

    private List<Metrology> getAllMetrologies() {

        return metrologyService.getAllMetrologies();
    }

    private Metrology selectedMetrology() {

        return TV_METROLOGY.getSelectionModel().getSelectedItem();
    }


    public void initialize() {

        updateOperatorComboBox();

        updateTestBenchComboBox();

        updateMetrologyTableView();

        updateTestBenchTableView();
    }

    @FXML
    public void updateOperatorComboBox() {

        List<Operator> allOperators = getAllOperators();

        CB_OPERATOR.setConverter(new StringConverter<>() {
            @Override
            public String toString(Operator operator) {
                if (operator != null) {
                    return operator.username() + "\n" + operator.firstName() + " " + operator.lastName();
                }
                return null;
            }

            @Override
            public Operator fromString(String string) {
                return null;
            }
        });

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

        CB_TESTBENCH.setConverter(new StringConverter<>() {
            @Override
            public String toString(TestBench testBench) {
                if (testBench != null) {
                    return testBench.name();
                }
                return null;
            }

            @Override
            public TestBench fromString(String s) {
                return null;
            }
        });

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
            getMetrologyListView();
            getOperatorsListView();

            TV_BENCH.getItems().addAll(testBench);
            TV_BENCH.setVisible(true);
        } else {
            TV_BENCH.setVisible(false);
        }
    }

    @FXML
    public void getMetrologyListView() {
        TC_B_METROLOGY.setCellValueFactory(param -> {
            List<Metrology> metrologyList = param.getValue().metrology();
            if (metrologyList != null && !metrologyList.isEmpty()) {
                StringBuilder metrologyInfo = new StringBuilder();
                for (Metrology metrology : metrologyList) {
                    metrologyInfo.append(metrology.iavInventory())
                            .append(", ")
                            .append(metrology.type())
                            .append("\n");
                }
                return new SimpleObjectProperty<>(metrologyInfo.toString());
            }else {
                return new SimpleObjectProperty<>("");
            }
        });
    }

    @FXML
    public void getOperatorsListView() {
        TC_B_OPERATOR.setCellValueFactory(param -> {
            List<Operator> operatorList = param.getValue().operator();
            if (operatorList != null && !operatorList.isEmpty()) {
                StringBuilder operatorInfo = new StringBuilder();
                for (Operator operator : operatorList) {
                    operatorInfo.append(operator.username())
                            .append(", ")
                            .append(operator.firstName())
                            .append(", ")
                            .append(operator.lastName())
                            .append("\n");
                }
                return new SimpleObjectProperty<>(operatorInfo.toString());
            }else {
                return new SimpleObjectProperty<>("");
            }
        });
    }

    @FXML
    public void updateMetrologyTableView() {

        List<Metrology> metrologyData = getAllMetrologies();
        StringConverter<Date> dateConverter = new DateStringConverter("dd.MM.yyyy");
        TV_METROLOGY.getItems().clear();

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
    public void updateTestBenchMaintenanceDate() throws JsonProcessingException {

        LocalDate localDate = DP_DATE.getValue();
        LocalDate maintenanceDate = localDate.plusYears(1);

        if (selectedTestBench != null) {
            selectedDate = Date.from(maintenanceDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            TestBench testBenchUpdate = new TestBench(
                    selectedTestBench.benchId(),
                    selectedTestBench.name(),
                    selectedTestBench.metrology(),
                    selectedTestBench.operator(),
                    selectedDate,
                    selectedTestBench.calibration());

            testBenchService.updateTestBenchMaintenanceByTestBenchId(selectedTestBench.benchId(), testBenchUpdate);

            CB_TESTBENCH.setValue(null);
            selectedTestBench = null;
            TV_BENCH.getItems().clear();
            updateTestBenchTableView();
        }
    }

    @FXML
    public void updateTestBenchCalibrationDate() throws JsonProcessingException {

        LocalDate localDate = DP_DATE.getValue();
        LocalDate calibrationDate = localDate.plusYears(1);

        if (selectedTestBench != null) {
            selectedDate = Date.from(calibrationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            TestBench testBenchUpdate = new TestBench(
                    selectedTestBench.benchId(),
                    selectedTestBench.name(),
                    selectedTestBench.metrology(),
                    selectedTestBench.operator(),
                    selectedTestBench.maintenance(),
                    selectedDate);

            testBenchService.updateTestBenchMaintenanceByTestBenchId(selectedTestBench.benchId(), testBenchUpdate);

            CB_TESTBENCH.setValue(null);
            selectedTestBench = null;
            TV_BENCH.getItems().clear();
            updateTestBenchTableView();
        }
    }

    @FXML
    public void onClick_PB_ADD_METROLOGY() {

        testBenchService.addMetrologyToTestBench(selectedMetrology().metrologyId(), selectedTestBench.benchId());
        CB_TESTBENCH.setValue(null);
        selectedTestBench = null;
        TV_METROLOGY.getItems().clear();
        TV_BENCH.getItems().clear();
        updateTestBenchComboBox();
        updateMetrologyTableView();
        updateTestBenchTableView();
    }

    @FXML
    public void onClick_PB_REMOVE_METROLOGY() {

        testBenchService.removeMetrologyFromTestBench(selectedMetrology().metrologyId(), selectedTestBench.benchId());
        CB_TESTBENCH.setValue(null);
        selectedTestBench = null;
        TV_METROLOGY.getItems().clear();
        TV_BENCH.getItems().clear();
        updateTestBenchComboBox();
        updateMetrologyTableView();
        updateTestBenchTableView();
    }

    @FXML
    public void onClick_PB_ADD_OPERATOR() {

        testBenchService.addOperatorToTestBench(selectedOperator.username(), selectedTestBench.benchId());
        CB_TESTBENCH.setValue(null);
        CB_OPERATOR.setValue(null);
        selectedTestBench = null;
        TV_BENCH.getItems().clear();
        updateTestBenchComboBox();
        updateOperatorComboBox();
        updateTestBenchTableView();
    }

    @FXML
    public void onClick_PB_REMOVE_OPERATOR() {

        testBenchService.removeOperatorFromTestBench(selectedOperator.username(), selectedTestBench.benchId());
        CB_TESTBENCH.setValue(null);
        CB_OPERATOR.setValue(null);
        selectedTestBench = null;
        TV_BENCH.getItems().clear();
        updateTestBenchComboBox();
        updateOperatorComboBox();
        updateTestBenchTableView();
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
