package de.iav.backend.service;

import de.iav.backend.model.Metrology;
import de.iav.backend.model.TestBench;
import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.repository.MetrologyRepository;
import de.iav.backend.repository.TestBenchOperatorRepository;
import de.iav.backend.repository.TestBenchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchService {

    private final TestBenchRepository testBenchRepository;
    private final MetrologyRepository metrologyRepository;
    private final TestBenchOperatorRepository testBenchOperatorRepository;


    public List<TestBench> getAllTestBenches() {
        return testBenchRepository.findAll();
    }

    public Optional<TestBench> getTestbenchById(String testBenchId) {
        return testBenchRepository.findById(testBenchId);
    }

    public TestBench setTestBenchMaintannaceDate(String testBenchId, int newMaintenanceDate) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        testBench.maintenance().setDate(newMaintenanceDate);
        testBenchRepository.save(testBench);
        return testBench;
    }

    public TestBench setTestBenchCalibrationDate(String testBenchId, int newCalibrationDate) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        testBench.calibration().setDate(newCalibrationDate);
        testBenchRepository.save(testBench);
        return testBench;
    }

    //Metrology related Methods
    public void addMetrologyToTestBench(String testBenchId, String metrologyId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchElementException("Das Messgerät\n"
                        + metrologyId
                        + "\nexistiert nicht"));
        testBench.metrology().add(metrology);
        testBenchRepository.save(testBench);
    }

    public void removeMetrologyFromTestBench(String testBenchId, String metrologyId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchElementException("Das Messgerät\n"
                        + metrologyId
                        + "\nexistier nicht!"));
        testBench.metrology().remove(metrology);
        testBenchRepository.save(testBench);
    }

    //TestBenchOperator related Methods

    public void addTestbenchOperatorToTestBench(String testBenchId, String testBenchOperatorId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        TestBenchOperator testBenchOperator = testBenchOperatorRepository
                .findById(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstandsfahrer\n"
                        + testBenchOperatorId
                        + "\nexistiert nicht!"));
        testBench.testBenchOperator().add(testBenchOperator);
        testBenchRepository.save(testBench);
    }

    public void removeTestBenchOperatorFromTestBench(String testBenchId, String testBenchOperatorId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        TestBenchOperator testBenchOperator = testBenchOperatorRepository
                .findById(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstandsfahrer\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        testBench.testBenchOperator().remove(testBenchOperator);
        testBenchRepository.save(testBench);
    }

}
