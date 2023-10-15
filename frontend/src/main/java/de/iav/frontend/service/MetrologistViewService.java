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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MetrologistViewService {

    private static MetrologistViewService instance;
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final HttpClient metrologistClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";
    private final MetrologyService metrologyService = MetrologyService.getInstance();

    public MetrologistViewService() {

    }

    public static synchronized MetrologistViewService getInstance() {
        if (instance == null) {
            instance = new MetrologistViewService();
        }
        return instance;
    }

    public List<Metrology> getAllMetrologies() {

        return metrologyService.getAllMetrologies();
    }

    public List<Metrology> getMetrologyByMaintenanceOrCalibrationDue() {
        return metrologyService.getMetrologyByMaintenanceOrCalibrationDue();
    }

    public void deleteMetrology(String metrologyId) {
        metrologyService.deleteMetrology(metrologyId);
    }

    public List<TestBench> getTestBenchesByMaintenanceOrCalibrationDue() {

        List<TestBench> allTestBenches = getAllTestBenches();
        List<TestBench> result = new ArrayList<>();

        int maxDiff = 5;
        Date currentDate = new Date();

        for (TestBench testBench : allTestBenches) {

            long maintenanceDiff = testBench.maintenance().getTime() - currentDate.getTime();
            int maintenanceDiffDays = (int) (maintenanceDiff / (24 * 60 * 60 * 1000));

            long calibrationDiff = testBench.calibration().getTime() - currentDate.getTime();
            int calibrationDiffDays = (int) (calibrationDiff / (24 * 60 * 60 * 1000));

            if (maintenanceDiffDays <= maxDiff || calibrationDiffDays <= maxDiff) {
                result.add(testBench);
            }
        }
        return result;
    }

    public List<TestBench> getAllTestBenches() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return metrologistClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBenchList)
                .join();
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
