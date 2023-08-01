package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;
import java.util.List;

@Document(collation = "TestBench")
public record TestBench(
        @MongoId
        String benchId,
        String name,
        @DBRef(lazy = true)
        List<Metrology> metrology,
        Date maintenance,
        Date calibration
) {
}
