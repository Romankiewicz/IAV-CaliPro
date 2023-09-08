package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.exceptions.NoSuchTestBenchException;
import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.model.Metrology;
import de.iav.backend.model.TestBench;
import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.repository.MetrologyRepository;
import de.iav.backend.repository.TestBenchOperatorRepository;
import de.iav.backend.repository.TestBenchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchService {

    private final TestBenchRepository testBenchRepository;
    private final MetrologyRepository metrologyRepository;
    private final TestBenchOperatorRepository testBenchOperatorRepository;


    public List<TestBench> listAllTestBenches() {
        return testBenchRepository.findAll();
    }

    public Optional<TestBench> findTestBenchById(String testBenchId) {
        return testBenchRepository.findById(testBenchId);
    }

    public TestBench addTestBench(TestBench testBenchToAdd){
        return testBenchRepository
                .save(
                        new TestBench(
                                testBenchToAdd.benchId(),
                                testBenchToAdd.name(),
                                new ArrayList<>(),
                                new ArrayList<>(),
                                testBenchToAdd.calibration(),
                                testBenchToAdd.maintenance()
                        )
                );
    }

    public TestBench setTestBenchMaintenanceDate(String testBenchId, int date, int month, int year) throws NoSuchTestBenchException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));

        testBench.maintenance().setDate(date);
        testBench.maintenance().setMonth(month);
        testBench.maintenance().setYear(year);
        testBenchRepository.save(testBench);
        return testBench;
    }

    public TestBench setTestBenchCalibrationDate(String testBenchId, int date, int month, int year) throws NoSuchTestBenchException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        testBench.calibration().setDate(date);
        testBench.calibration().setMonth(month);
        testBench.calibration().setYear(year);
        testBenchRepository.save(testBench);
        return testBench;
    }

    //Metrology related Methods
    public void addMetrologyToTestBench(String testBenchId, String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        testBench.metrology().add(metrology);
        testBenchRepository.save(testBench);
    }

    public void removeMetrologyFromTestBench(String testBenchId, String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        testBench.metrology().remove(metrology);
        testBenchRepository.save(testBench);
    }

    //TestBenchOperator related Methods

    public void addTestBenchOperatorToTestBench(String testBenchId, String testBenchOperatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        TestBenchOperator testBenchOperator = testBenchOperatorRepository
                .findById(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorException(testBenchOperatorId));
        testBench.testBenchOperator().add(testBenchOperator);
        testBenchOperator.testBench().add(testBench);
        testBenchOperatorRepository.save(testBenchOperator);
        testBenchRepository.save(testBench);
    }

    public void removeTestBenchOperatorFromTestBench(String testBenchId, String testBenchOperatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorException {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        TestBenchOperator testBenchOperator = testBenchOperatorRepository
                .findById(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorException(testBenchOperatorId));
        testBenchOperator.testBench().remove(testBench);
        testBench.testBenchOperator().remove(testBenchOperator);
        testBenchRepository.save(testBench);
        testBenchOperatorRepository.save(testBenchOperator);
    }

}
