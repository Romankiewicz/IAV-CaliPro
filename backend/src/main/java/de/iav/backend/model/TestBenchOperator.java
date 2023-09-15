package de.iav.backend.model;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document(collection = "Operator")
public record TestBenchOperator(
        @MongoId
        String operatorId,
        @Indexed(unique = true)
        String username,
        String password,
        String firstName,
        String lastName,
        String eMail,
        @DBRef(lazy = true)
        List<TestBench> testBench
        ) {
}
