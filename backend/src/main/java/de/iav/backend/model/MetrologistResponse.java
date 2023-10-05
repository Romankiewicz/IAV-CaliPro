package de.iav.backend.model;

public record MetrologistResponse(

        String metrologistId,
        String username,
        String firstName,
        String lastName,
        String eMail
) {
}
