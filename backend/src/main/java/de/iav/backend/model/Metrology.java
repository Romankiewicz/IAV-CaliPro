package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.Date;

@Document(collection = "Metrology")
public record Metrology(
        @MongoId
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        LocalDate maintenance,
        LocalDate calibration
) {
}
