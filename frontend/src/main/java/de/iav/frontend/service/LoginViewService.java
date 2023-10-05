package de.iav.frontend.service;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;

public class LoginViewService {

    private static LoginViewService instance;

    private final HttpClient loginClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");

    public LoginViewService() {

    }

    public static synchronized LoginViewService getInstance() {
        if (instance == null) {
            instance = new LoginViewService();
        }
        return instance;
    }



}
