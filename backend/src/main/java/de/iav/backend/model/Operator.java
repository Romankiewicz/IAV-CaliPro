package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.iav.backend.security.UserRole;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "operator")
public record Operator(
        @MongoId
        String operatorId,
        @Indexed(unique = true)
        String username,
        String firstName,
        String lastName,
        String email,
        @DBRef(lazy = true)
        @JsonIgnoreProperties("perators")
        @JsonBackReference
        List<TestBench> testBench,
        UserRole role
) {
}
