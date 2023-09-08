package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.exceptions.NoSuchTestBenchException;
import de.iav.backend.exceptions.NoSuchTestBenchOperatorExeption;
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
        return testBenchService.listAllTestBenches();
    }

    @GetMapping("/{testBenchId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<TestBench> getTestBenchById(@PathVariable String testBenchId){
        return testBenchService.findTestBenchById(testBenchId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestBench addTestBench(@RequestBody TestBench testBenchToAdd){
        return testBenchService.addTestBench(testBenchToAdd);
    }

    @PutMapping("/{testBenchId}/maintenance")
    @ResponseStatus(HttpStatus.CREATED)
    public TestBench setTestBenchMaintenanceDate(@PathVariable String testBenchId, @RequestBody int date, int month, int year) throws NoSuchTestBenchException {
        return testBenchService.setTestBenchMaintenanceDate(testBenchId, date, month, year);
    }

    @PutMapping("/{testBenchId}/calibration")
    @ResponseStatus(HttpStatus.CREATED)
    public TestBench setTestBenchCalibrationDate(@PathVariable String testBenchId, @RequestBody int date, int month, int year) throws NoSuchTestBenchException {
        return testBenchService.setTestBenchCalibrationDate(testBenchId, date, month, year);
    }

    @PutMapping("/metrology/{testBenchId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addMetrologyToTestBench(@PathVariable String testBenchId, @RequestBody String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        testBenchService.addMetrologyToTestBench(testBenchId, metrologyId);
    }

    @DeleteMapping("/metrology/{testBenchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeMetrologyFromTestBench(@PathVariable String testBenchId, @RequestBody String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        testBenchService.removeMetrologyFromTestBench(testBenchId, metrologyId);
    }

    @PutMapping("/operator/{testBenchId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTestBenchOperatorToTestBench(@PathVariable String testBenchId, @RequestBody String operatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorExeption {
        testBenchService.addTestBenchOperatorToTestBench(testBenchId, operatorId);
    }

    @DeleteMapping("/operator/{testBenchId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTestBechOperatorFromTestBench(@PathVariable String testBenchId, @RequestBody String operatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorExeption {
        testBenchService.removeTestBenchOperatorFromTestBench(testBenchId, operatorId);
    }

}
