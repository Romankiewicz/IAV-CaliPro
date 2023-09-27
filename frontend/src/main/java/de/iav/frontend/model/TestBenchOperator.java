package de.iav.frontend.model;

import java.util.List;

public record TestBenchOperator(
        String operatorId,
        String username,
        String password,
        String firstName,
        String lastName,
        String eMail,
        List<TestBench> testBench,
        UserRole role
        ) {
}
