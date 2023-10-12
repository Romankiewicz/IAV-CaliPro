package de.iav.frontend.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class AuthenticationService {

    private static AuthenticationService instance;
    private String username;
    private String sessionId;
    private String errorMessage;

    private final HttpClient authenticationClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private String IAVCALIPRO_URL_BACKEND = System.getenv("BACKEND_IAVCALIPRO_URI");

    private AuthenticationService() {

    }

    public static synchronized AuthenticationService getInstance() {
        if (instance == null) {
            instance = new AuthenticationService();
        }
        return instance;
    }

    public boolean addMetrologist(String username, String password) {
        try {
            UserRequest userRequest = new UserRequest(username, password);
            String requestBody = objectMapper.writeValueAsString(userRequest);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/api/users/register/metrologist"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();
            var response = authenticationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            int statusCode = response.join().statusCode();

            return handleStatusCheckAndSetSessionId(statusCode, response, "Registrierung fehlgeschlagen.");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private boolean handleStatusCheckAndSetSessionId(int statusCode, CompletableFuture<HttpResponse<String>> response, String errorMessage) {
        if (statusCode == 200) {
            setUsername(response.join().body());
            String responseSessionId = response.join().headers().firstValue("Set-Cookie").orElseThrow();
            setSessionId(responseSessionId.substring(11, responseSessionId.indexOf(";")));
            return true;
        } else {
            if (statusCode == 401) {
                setErrorMessage(errorMessage);
            } else {
                setErrorMessage("Ups da ist wohl was schief gelaufen...");
            }
            return false;
        }
    }

    public boolean login(String username, String password) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(IAVCALIPRO_URL_BACKEND + "/api/users/login"))
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes()))
                .build();

        var response = authenticationClient.sendAsync(request, HttpResponse.BodyHandlers.ofString());
        int statusCode = response.join().statusCode();

        return handleStatusCheckAndSetSessionId(statusCode, response, "Login fehlgeschlagen!");
    }

    public String getUsername() {
        return username;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getErrorMassage() {
        return errorMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpClient getAuthenticationClient() {
        return this.authenticationClient;
    }
}
