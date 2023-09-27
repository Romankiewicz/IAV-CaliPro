package de.iav.frontend.model;

public record Metrologist(
        String metrologistId,
        String username,
        String password,
        String firstName,
        String lastName,
        String eMail,
        UserRole role
) {
}
