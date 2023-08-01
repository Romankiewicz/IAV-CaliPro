package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collation = "Operator")
public record TestBenchOperator(
        @MongoId
        String operatorId,
        String firstName,
        String lastName,
        String eMail,
        @DBRef(lazy = true)
        TestBench testBench
        ) {
}
