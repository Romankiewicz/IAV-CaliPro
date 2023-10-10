package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.MongoId;

public record OperatorDTO(

        String operatorId,
        String username,
        String firstName,
        String lastName,
        String email
) {
}

