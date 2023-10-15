package de.iav.frontend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.frontend.model.Metrology;
import de.iav.frontend.security.AuthenticationService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetrologyService {

    private static MetrologyService instance;
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final HttpClient metrologyClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";




    public MetrologyService() {

    }

    public static synchronized MetrologyService getInstance() {

        if (instance == null) {
            instance = new MetrologyService();
        }
        return instance;
    }

    public List<Metrology> getMetrologyByMaintenanceOrCalibrationDue() {

        List<Metrology> allMetrologies = getAllMetrologies();
        List<Metrology> result = new ArrayList<>();

        int maxDiff = 5;
        Date currentDate = new Date();

        for (Metrology metrology : allMetrologies) {

            long maintenanceDiff = metrology.maintenance().getTime() - currentDate.getTime();
            int maintenanceDiffDays = (int) (maintenanceDiff / (24 * 60 * 60 * 1000));

            long calibrationDiff = metrology.calibration().getTime() - currentDate.getTime();
            int calibrationDiffDays = (int) (calibrationDiff / (24 * 60 * 60 * 1000));

            if(maintenanceDiffDays <= maxDiff || calibrationDiffDays <= maxDiff) {
                result.add(metrology);
            }
        }
        return result;
    }

    public List<Metrology> getAllMetrologies() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND +"metrology"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrologyList)
                .join();
    }

    public Metrology getMetrologyById(String metrologyId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology/" + metrologyId))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .GET()
                .build();

        return metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrology)
                .join();
    }

    public void addMetrology(Metrology metrologyToAdd) throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(metrologyToAdd);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology"))
                .header("Content-Type", JSON)
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToMetrology)
                .join();
    }

    public void updateMetrologyMaintenanceByMetrologyId(String metrologyId, Metrology maintenanceUpdate) throws JsonProcessingException {

            String requestBody = objectMapper.writeValueAsString(maintenanceUpdate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology/" + metrologyId))
                    .header("Content-Type", JSON)
                    .header("Accept", JSON)
                    .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(this::mapToMetrology)
                    .join();
    }

    public void updateMetrologyCalibrationByMetrologyId(String metrologyId, Metrology calibrationUpdate) throws JsonProcessingException {

            String requestBody = objectMapper.writeValueAsString(calibrationUpdate);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "metrology/" + metrologyId))
                    .header("Content-Type", JSON)
                    .header("Accept", JSON)
                    .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
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
        metrologyClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
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
}
