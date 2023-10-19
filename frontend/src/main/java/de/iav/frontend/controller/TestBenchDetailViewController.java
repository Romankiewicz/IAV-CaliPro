package de.iav.frontend.controller;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.Operator;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.service.MetrologyService;
import de.iav.frontend.service.OperatorViewService;
import de.iav.frontend.service.SceneSwitchService;
import de.iav.frontend.service.TestBenchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
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


    public void initialize() {
        List<Metrology> allMetrologies = metrologyService.getAllMetrologies();
        List<TestBench> allTestBenches = testBenchService.getAllTestBenches();
        List<Operator> allOperators = operatorViewService.getAllOperators();

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
