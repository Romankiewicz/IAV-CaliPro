package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrologist;
import de.iav.frontend.model.MetrologistDTO;
import de.iav.frontend.model.TestBenchOperator;
import de.iav.frontend.model.TestBenchOperatorDTO;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RegistrationViewService {

    private static RegistrationViewService instance;
    private final HttpClient registrationClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    public RegistrationViewService() {

    }

    public static synchronized RegistrationViewService getInstance() {
        if (instance == null) {
            instance = new RegistrationViewService();
        }
        return instance;
    }

    public Metrologist addMetrologist(MetrologistDTO metrologistToAdd, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(metrologistToAdd);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/api/metrologist/register"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" + sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToMetrologist(responseBody))
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public TestBenchOperator addTestbenchOperator(TestBenchOperatorDTO operatorToAdd, String sessionId) {
        try {
            String requestBody = objectMapper.writeValueAsString(operatorToAdd);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/api/operators/register"))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "JSESSIONID=" +sessionId)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return registrationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToOperator(responseBody))
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Metrologist mapToMetrologist(String responseBody) {
        System.out.println(responseBody);
        try {
            return objectMapper.readValue(responseBody, Metrologist.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private TestBenchOperator mapToOperator(String responseBody) {
        System.out.println(responseBody);
        try {
            return objectMapper.readValue(responseBody, TestBenchOperator.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
