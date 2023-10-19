package de.iav.frontend.model;

import de.iav.frontend.security.UserRole;

public record Metrologist(
        String metrologistId,
        String username,
        String password,
        String firstName,
        String lastName,
        String email,
        UserRole role
) {
}
