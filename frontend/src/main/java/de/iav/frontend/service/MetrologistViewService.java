package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class MetrologistViewService {

    private static  MetrologistViewService instance;
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final HttpClient metrologistClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";

    public MetrologistViewService() {

    }

    public static synchronized MetrologistViewService getInstance() {
        if (instance == null) {
            instance = new MetrologistViewService();
        }
        return instance;
    }

    public List<Metrology> getAllMetrologies() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND +"metrology"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return metrologistClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrologyList)
                .join();
    }

    public List<TestBench> getAllTestBenches() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND +"testbenches"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return metrologistClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBenchList)
                .join();
    }

    public Metrology getMetrologyById(String metrologyId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology/" + metrologyId))
                .header("Accept", JSON)
                .header("Cookie", "JSESSEIONID=" +authenticationService.getSessionId())
                .GET()
                .build();

        return metrologistClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrology)
                .join();
    }

    public void deleteMetrology(String metrologyId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology/" + metrologyId))
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .DELETE()
                .build();
        metrologistClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
    }



    private List<Metrology> mapToMetrologyList(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Metrology mapToMetrology(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, Metrology.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private List<TestBench> mapToTestBenchList(String responseBody) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
