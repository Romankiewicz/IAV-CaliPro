package de.iav.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document(collection = "testBench")
public record TestBench(
        @MongoId
        String benchId,
        String name,
        @DBRef
        @JsonIgnoreProperties("testBench")
        List<Metrology> metrology,
        @DBRef(lazy = true)
        @JsonIgnoreProperties("testBench")
        List<Operator> operator,
        Date maintenance,
        Date calibration
) {
}
