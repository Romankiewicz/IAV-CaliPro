package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrologist;
import de.iav.frontend.model.MetrologistDTO;
import de.iav.frontend.model.Operator;
import de.iav.frontend.model.OperatorDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegistrationViewService {

    private static RegistrationViewService instance;
    private final HttpClient registrationClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";
    public RegistrationViewService() {

    }

    public static synchronized RegistrationViewService getInstance() {
        if (instance == null) {
            instance = new RegistrationViewService();
        }
        return instance;
    }

    public void addMetrologist(MetrologistDTO metrologistToAdd, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(metrologistToAdd);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrologist"))
                    .header("Content-Type", JSON)
                    .header("Accept", JSON)
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToMetrologist)
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Operator addTestbenchOperator(OperatorDTO operatorToAdd, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(operatorToAdd);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "operators"))
                    .header("Content-Type", JSON)
                    .header("Accept", JSON)
                    .header("Cookie", "JSESSIONID=" +sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToOperator)
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
