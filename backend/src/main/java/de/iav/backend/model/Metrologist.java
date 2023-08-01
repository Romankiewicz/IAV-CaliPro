package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("Metrologist")
public record Metrologist(
        @MongoId
        String MetrologistId,
        String firstName,
        String lastName,
        String eMail
) {
}
