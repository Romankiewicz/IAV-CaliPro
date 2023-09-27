package de.iav.backend.model;

import java.util.List;

public record TestBenchOperatorResponse(
        String operatorId,
        String username,
        String firstName,
        String lastName,
        String eMail,
        List<TestBench> testBench) {
}
