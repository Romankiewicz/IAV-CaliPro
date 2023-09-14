package de.iav.backend.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("Metrologist")
public record Metrologist(
        @MongoId
        String MetrologistId,
        @Indexed(unique = true)
        String username,
        String password,
        String firstName,
        String lastName,
        String eMail
) {
}
