package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrologist;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LoginViewService {

    private static LoginViewService instance;

    private final HttpClient loginClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();

    public LoginViewService() {

    }

    public static synchronized LoginViewService getInstance() {
        if (instance == null) {
            instance = new LoginViewService();
        }
        return instance;
    }

    public Metrologist loginAsMetrologist() {
        String username = AuthenticationService.getInstance().getUsername();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "users/login"))
                .header("Accept", "application/json")
                .header("Cookie", "JSESSIONID=" + AuthenticationService.getInstance().getSessionId())
                .build();

        return loginClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrologist)
                .join();
    }


    private Metrologist mapToMetrologist(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Metrologist.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
