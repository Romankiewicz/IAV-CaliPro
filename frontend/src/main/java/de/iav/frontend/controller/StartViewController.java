package de.iav.frontend.controller;

import de.iav.frontend.service.SceneSwitchService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartViewController {

    private String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");

    public void onClick_PB_REGISTRATION(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToRegistrationView(event);

    }

    public void onClick_PB_LOGIN(ActionEvent event) throws IOException {
        SceneSwitchService.getInstance().switchToLoginView(event);
    }
}
