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

public class TestBenchService {

    private static TestBenchService instance;
    private final AuthenticationService authenticationService = AuthenticationService.getInstance();
    private final HttpClient testBenchClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");
    private static final String JSON = "application/json";



    public TestBenchService() {

    }

    public static synchronized TestBenchService getInstance() {

        if (instance == null) {
            instance = new TestBenchService();
        }
        return instance;
    }

    public TestBench getTestBenchById(String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .GET()
                .build();

        return testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBench)
                .join();
    }

    public List<TestBench> getAllTestBenches() {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches"))
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .build();

        return testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBenchList)
                .join();
    }

    public void addTestBench(TestBench testBenchToAdd) throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(testBenchToAdd);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches"))
                .header("Content-Type", JSON)
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBench)
                .join();
    }

    public void updateTestBenchMaintenanceByTestBenchId(String benchId, TestBench maintenanceUpdate) throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(maintenanceUpdate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId))
                .header("Content-Type", JSON)
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBench)
                .join();
    }

    public void updateTestBenchCalibrationByTestBenchId(String benchId, Metrology calibrationUpdate) throws JsonProcessingException {

        String requestBody = objectMapper.writeValueAsString(calibrationUpdate);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId))
                .header("Content-Type", JSON)
                .header("Accept", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(this::mapToTestBench)
                .join();
    }

    public void addMetrologyToTestBench(String metrologyId, String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId + "/metrology/" + metrologyId))
                .header("Content-Type", JSON)
                .header("Acceot", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
    }

    public void removeMetrologyFromTestBench(String metrologyId, String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId + "/metrology/" + metrologyId))
                .header("Content-Type", JSON)
                .header("Acceot", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .DELETE()
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
    }

    public void addOperatorToTestBench(String username, String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId + "/operator/" + username))
                .header("Content-Type", JSON)
                .header("Acceot", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .PUT(HttpRequest.BodyPublishers.ofString(""))
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
    }

    public void removeOperatorFromTestBench(String username, String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId + "/operator/" + username))
                .header("Content-Type", JSON)
                .header("Acceot", JSON)
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .DELETE()
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
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

    public void deleteTestBench(String benchId) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "testbenches/" + benchId))
                .header("Cookie", "JSESSIONID=" + authenticationService.getSessionId())
                .DELETE()
                .build();
        testBenchClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .join();
    }

    private TestBench mapToTestBench(String responseBody) {

        try {
            return objectMapper.readValue(responseBody, TestBench.class);
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
