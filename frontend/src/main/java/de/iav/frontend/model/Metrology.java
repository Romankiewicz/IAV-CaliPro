package de.iav.frontend.model;

import java.util.Date;

public record Metrology(
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        Date maintenance,
        Date calibration
) {
}
