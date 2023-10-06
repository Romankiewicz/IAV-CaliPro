package de.iav.backend.model;

import de.iav.backend.security.UserRole;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "Operator")
public record Operator(
        @MongoId
        String operatorId,
        @Indexed(unique = true)
        String username,
        String firstName,
        String lastName,
        String eMail,
        @DBRef(lazy = true)
        List<TestBench> testBench,
        UserRole role
) {
}
