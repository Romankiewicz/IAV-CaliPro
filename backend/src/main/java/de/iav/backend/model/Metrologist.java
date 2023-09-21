package de.iav.backend.model;

import de.iav.backend.security.UserRole;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "Metrologist")
public record Metrologist(
        @MongoId
        String metrologistId,
        @Indexed(unique = true)
        String username,
        String password,
        String firstName,
        String lastName,
        String eMail,
        UserRole role
) {
}
