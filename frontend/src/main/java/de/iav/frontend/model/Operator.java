package de.iav.frontend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.iav.frontend.security.UserRole;

import java.util.List;

public record Operator(
        @JsonIgnoreProperties
        String operatorId,
        String username,
        String password,
        String firstName,
        String lastName,
        String email,
        List<TestBench> testBench,
        UserRole role
        ) {
}
