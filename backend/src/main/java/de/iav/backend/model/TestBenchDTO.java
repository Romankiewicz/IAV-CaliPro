package de.iav.backend.model;

import java.util.Date;
import java.util.List;

public record TestBenchDTO(
        String testBenchId,
        String name,
        List<Metrology> metrology,
        List<Operator> operator,
        Date maintenance,
        Date calibration
) {
}
