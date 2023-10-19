package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.exceptions.NoSuchTestBenchException;
import de.iav.backend.model.TestBench;
import de.iav.backend.model.TestBenchDTO;
import de.iav.backend.service.TestBenchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testbenches")
@RequiredArgsConstructor
public class TestBenchController {

    private final TestBenchService testBenchService;

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TestBench> getAllTestBenches() {
        return testBenchService.listAllTestBenches();
    }

    @GetMapping("/{testBenchId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TestBench getTestBenchById(@PathVariable String testBenchId) throws NoSuchTestBenchException {
        return testBenchService.findTestBenchById(testBenchId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestBench addTestBench(@RequestBody TestBenchDTO testBenchToAdd) {
        return testBenchService.addTestBench(testBenchToAdd);
    }

    @PutMapping("/{testBenchId}/metrology/{metrologyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMetrologyToTestBench(@PathVariable String testBenchId, @PathVariable String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        testBenchService.addMetrologyToTestBench(testBenchId, metrologyId);
    }

    @DeleteMapping("/{testBenchId}/metrology/{metrologyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMetrologyFromTestBench(@PathVariable String testBenchId, @PathVariable String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        testBenchService.removeMetrologyFromTestBench(testBenchId, metrologyId);
    }

    @PutMapping("/{testBenchId}/operator/{username}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addOperatorToTestBench(@PathVariable String testBenchId, @PathVariable String username) throws NoSuchTestBenchException {
        testBenchService.addOperatorToTestBench(testBenchId, username);
    }

    @DeleteMapping("/{testBenchId}/operator/{username}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOperatorFromTestBench(@PathVariable String testBenchId, @PathVariable String username) throws NoSuchTestBenchException {
        testBenchService.removeOperatorFromTestBench(testBenchId, username);
    }

    @PutMapping("/{testBenchId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TestBench updateTestBenchByBenchId(@PathVariable String testBenchId, @RequestBody TestBenchDTO testBenchUpdate) throws NoSuchTestBenchException {
        return testBenchService.updateTestBenchById(testBenchId, testBenchUpdate);
    }

}
