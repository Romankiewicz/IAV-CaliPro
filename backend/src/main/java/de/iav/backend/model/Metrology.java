package de.iav.backend.model;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Document("Metrology")
public record Metrology(
        @MongoId
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        Date maintenance,
        Date calibration
) {
}
