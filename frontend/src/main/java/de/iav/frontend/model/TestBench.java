package de.iav.frontend.model;

import java.util.Date;
import java.util.List;

public record TestBench(
        String benchId,
        String name,
        List<Metrology> metrology,
        List<Operator> operator,
        Date maintenance,
        Date calibration
) {
}
