package de.iav.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iav.frontend.model.Metrology;
import de.iav.frontend.service.MetrologyService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
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

public class MetrologyDetailViewController {

    @FXML
    private ChoiceBox<Metrology> CB_METROLOGY;
    @FXML
    private DatePicker DP_DATE;
    private Date selectedDate;
    private Metrology selectedMetrology;

    @FXML
    private TableView<Metrology> TV_METROLOGY_DETAIL;
    @FXML
    private TableColumn<Metrology, String> TC_M_ID_DETAIL;
    @FXML
    private TableColumn<Metrology, String> TC_M_INVENTORY_DETAIL;
    @FXML
    private TableColumn<Metrology, String> TC_M_MANUFACTURER_DETAIL;
    @FXML
    private TableColumn<Metrology, String> TC_M_TYPE_DETAIL;
    @FXML
    private TableColumn<Metrology, Date> TC_M_MAINTENANCE_DETAIL;
    @FXML
    private TableColumn<Metrology, Date> TC_M_CALIBRATION_DETAIL;

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final MetrologyService metrologyService = MetrologyService.getInstance();


    public void initialize() {

        List<Metrology> allMetrologies = metrologyService.getAllMetrologies();
        CB_METROLOGY.getItems().clear();
        CB_METROLOGY.getItems().addAll(allMetrologies);

        CB_METROLOGY.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedMetrology = metrologyService.getMetrologyById(newValue.metrologyId());
                updateTableView();
            }
        });
        updateTableView();
    }

    public List<Metrology> getAllMetrologies() {

        return metrologyService.getAllMetrologies();
    }

    @FXML
    public void updateChoiceBox() {

        List<Metrology> allMetrologies = getAllMetrologies();
        CB_METROLOGY.getItems().clear();
        CB_METROLOGY.getItems().addAll(allMetrologies);

        CB_METROLOGY.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedMetrology = metrologyService.getMetrologyById(newValue.metrologyId());
                updateTableView();
            }
        });
    }

    @FXML
    public void updateTableView() {


        if (selectedMetrology != null) {
            List<Metrology> metrologies = new ArrayList<>();
            metrologies.add(selectedMetrology);

            StringConverter<Date> dateStringConverter = new DateStringConverter("dd.MM.yyyy");
            TV_METROLOGY_DETAIL.getItems().clear();

            TC_M_ID_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().metrologyId()));
            TC_M_INVENTORY_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().iavInventory()));
            TC_M_MANUFACTURER_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().manufacturer()));
            TC_M_TYPE_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().type()));
            TC_M_MAINTENANCE_DETAIL.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
            TC_M_MAINTENANCE_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().maintenance()));
            TC_M_CALIBRATION_DETAIL.setCellFactory(TextFieldTableCell.forTableColumn(dateStringConverter));
            TC_M_CALIBRATION_DETAIL.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().calibration()));
            TV_METROLOGY_DETAIL.getItems().addAll(metrologies);
            TV_METROLOGY_DETAIL.setVisible(true);
        } else {
            TV_METROLOGY_DETAIL.setVisible(false);
        }
    }

    @FXML
    public void updateMaintenanceDate() throws JsonProcessingException {

        LocalDate localDate = DP_DATE.getValue();
        LocalDate maintenanceDate = localDate.plusYears(1);

        if (selectedMetrology != null) {
            selectedDate = Date.from(maintenanceDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Metrology metrologyUpdate = new Metrology(
                    selectedMetrology.metrologyId(),
                    selectedMetrology.iavInventory(),
                    selectedMetrology.manufacturer(),
                    selectedMetrology.type(),
                    selectedDate,
                    selectedMetrology.calibration());

            metrologyService.updateMetrologyMaintenanceByMetrologyId(selectedMetrology.metrologyId(), metrologyUpdate);

            CB_METROLOGY.setValue(null);
            TV_METROLOGY_DETAIL.getItems().clear();
            updateChoiceBox();
            updateChoiceBox();
        }
    }

    @FXML
    public void updateCalibrationDate() throws JsonProcessingException {

        LocalDate localDate = DP_DATE.getValue();
        LocalDate calibrationDate = localDate.plusYears(1);

        if (selectedMetrology != null) {
            selectedDate = Date.from(calibrationDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Metrology metrologyUpdate = new Metrology(
                    selectedMetrology.metrologyId(),
                    selectedMetrology.iavInventory(),
                    selectedMetrology.manufacturer(),
                    selectedMetrology.type(),
                    selectedMetrology.maintenance(),
                    selectedDate);

            metrologyService.updateMetrologyCalibrationByMetrologyId(selectedMetrology.metrologyId(), metrologyUpdate);

            CB_METROLOGY.setValue(null);
            TV_METROLOGY_DETAIL.getItems().clear();
            updateChoiceBox();
            updateChoiceBox();
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