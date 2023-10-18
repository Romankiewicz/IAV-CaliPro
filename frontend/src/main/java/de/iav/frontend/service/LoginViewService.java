package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrologist;
import de.iav.frontend.model.Operator;
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
    private static final String JSON = "application/json";
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
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return loginClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrologist)
                .join();
    }

    public Operator loginAsOperator() {
        String username = AuthenticationService.getInstance().getUsername();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "users/login"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return loginClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToOperator)
                .join();
    }


    private Metrologist mapToMetrologist(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Metrologist.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Operator mapToOperator(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Operator.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
