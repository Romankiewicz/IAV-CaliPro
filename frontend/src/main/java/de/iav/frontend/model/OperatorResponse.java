package de.iav.frontend.model;

import java.util.List;

public record OperatorResponse(
        String operatorId,
        String username,
        String firstName,
        String lastName,
        String eMail,
        List<TestBench> testBench
) {
}
