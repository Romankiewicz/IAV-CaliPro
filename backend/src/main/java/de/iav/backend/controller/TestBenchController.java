package de.iav.backend.controller;

import de.iav.backend.model.TestBench;
import de.iav.backend.service.TestBenchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/testbenches")
@RequiredArgsConstructor
public class TestBenchController {

    private final TestBenchService testBenchService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TestBench> getAllTestBenches(){
        return testBenchService.getAllTestBenches();
    }

    @GetMapping("/{testBenchId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<TestBench> getTestBenchById(@PathVariable String testBenchId){
        return testBenchService.getTestBenchById(testBenchId);
    }


}
