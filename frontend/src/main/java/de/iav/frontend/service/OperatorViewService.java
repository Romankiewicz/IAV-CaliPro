package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Operator;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class OperatorViewService {

    private static OperatorViewService instance;
    private final HttpClient operatorClient = HttpClient.newHttpClient();
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";


    public static synchronized OperatorViewService getInstance() {

        if (instance == null) {
            instance = new OperatorViewService();
        }
        return instance;
    }

    public Operator getLoginOperator() {

        String username = authenticationService.getUsername();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/operators/username/" + username))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();
        return operatorClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToOperator)
                .join();
    }

    private Operator mapToOperator(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Operator.class);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
