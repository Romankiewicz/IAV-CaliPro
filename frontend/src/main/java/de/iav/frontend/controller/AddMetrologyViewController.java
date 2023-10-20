package de.iav.frontend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.iav.frontend.model.Metrology;
import de.iav.frontend.security.AuthenticationService;
import de.iav.frontend.service.IdService;
import de.iav.frontend.service.MetrologyService;
import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class AddMetrologyViewController {

    @FXML
    private AnchorPane AP;
    @FXML
    private Button PB_ADD;
    @FXML
    private TextField TF_INVENTORY;
    @FXML
    private TextField TF_MANUFACTURER;
    @FXML
    private TextField TF_TYPE;
    @FXML
    private Label LF_ERROR;
    @FXML
    private DatePicker DP_MAINTENANCE;
    @FXML
    private DatePicker DP_CALIBRATION;

    @FXML
    private final SceneSwitchService sceneSwitchService = SceneSwitchService.getInstance();
    @FXML
    private final MetrologyService metrologyService = MetrologyService.getInstance();
    @FXML
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    @FXML
    private final IdService idService = IdService.getInstance();

    public AddMetrologyViewController() {
    }

    @FXML
    public void initialize() {

        PB_ADD.setOnAction(this::onClick_PB_ADD_addMetrology);

        AP.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                onClick_PB_ADD_addMetrology(new ActionEvent(PB_ADD, null));
                event.consume();
            }
        });
    }

    @FXML
    public void onClick_PB_HOME(ActionEvent event) throws IOException {
        authenticationService.logout();
        sceneSwitchService.switchToStartView(event);
    }

    @FXML
    public void onClick_PB_CLOSE(ActionEvent event) throws IOException {
        sceneSwitchService.switchToMetrologistView(event);
    }


    @FXML
    public void onClick_PB_ADD_addMetrology(ActionEvent event) {

        if (isEveryTextFieldValid()) {
            String iavInventory = TF_INVENTORY.getText();
            String manufacturer = TF_MANUFACTURER.getText();
            String type = TF_TYPE.getText();
            LocalDate localDateMaintenanceSelection = DP_MAINTENANCE.getValue();
            LocalDate localDateMaintenance = localDateMaintenanceSelection.plusYears(1);
            LocalDate localDateCalibrationSelection = DP_CALIBRATION.getValue();
            LocalDate localDateCalibration = localDateCalibrationSelection.plusYears(1);

            Date maintenanceDate = Date.from(localDateMaintenance.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date calibrationDate = Date.from(localDateCalibration.atStartOfDay(ZoneId.systemDefault()).toInstant());

            Metrology metrologyToAdd = new Metrology(
                    idService.generateRandomId(),
                    iavInventory,
                    manufacturer,
                    type,
                    maintenanceDate,
                    calibrationDate
                    );

            try {
                metrologyService.addMetrology(metrologyToAdd);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            try {
                sceneSwitchService.switchToMetrologistView(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }



    private boolean isEveryTextFieldValid() {
        if (TF_INVENTORY.getText() == null || TF_INVENTORY.getText().isEmpty()) {
            LF_ERROR.setText("Bitte Inventarnummer eingeben");
            return false;
        } else if (TF_MANUFACTURER.getText() == null || TF_MANUFACTURER.getText().isEmpty()) {
            LF_ERROR.setText("Btte Hersteller eingeben");
            return false;
        } else if (TF_TYPE.getText() == null || TF_TYPE.getText().isEmpty()) {
            LF_ERROR.setText("Bitte Herstellerbezeichnung eingeben");
            return false;
        } else {
            LF_ERROR.setText("");
            return true;
        }
    }
}
