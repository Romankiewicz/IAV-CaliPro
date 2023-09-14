package de.iav.backend.model;

public record MetrologistResponse(

        String username,
        String password,
        String firstName,
        String lastName,
        String eMail
) {
}
