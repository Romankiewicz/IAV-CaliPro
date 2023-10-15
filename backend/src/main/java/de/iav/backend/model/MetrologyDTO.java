package de.iav.backend.model;

import java.util.Date;

public record MetrologyDTO(
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        Date maintenance,
        Date calibration
) {


}
