package de.iav.backend.model;

import java.time.LocalDate;

public record TestBenchDTO(
       String benchId,
       String name,
       LocalDate maintenance,
       LocalDate calibration
) {
}
