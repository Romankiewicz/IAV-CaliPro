package de.iav.backend.controller;

import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.service.TestBenchOperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class TestBenchOperatorController {

    private final TestBenchOperatorService testBenchOperatorService;

    @GetMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<TestBenchOperator> getTestBenchOperatorById(@PathVariable String operatorId){
        return testBenchOperatorService.getTestBenchOperatorById(operatorId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestBenchOperator addTestBenchOperator(@RequestBody TestBenchOperator testBenchOperatorToAdd){
        return testBenchOperatorService.addTestBenchOperator(testBenchOperatorToAdd);
    }
}
