package de.iav.backend.model;

import java.time.LocalDate;

public record MetrologyDTO(
        String metrologyId,
        String iavInventory,
        String manufacturer,
        String type,
        LocalDate maintenance,
        LocalDate calibration
) {


}
