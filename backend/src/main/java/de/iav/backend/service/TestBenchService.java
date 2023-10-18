package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.exceptions.NoSuchTestBenchException;
import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.model.Metrology;
import de.iav.backend.model.Operator;
import de.iav.backend.model.TestBench;
import de.iav.backend.model.TestBenchDTO;
import de.iav.backend.repository.MetrologyRepository;
import de.iav.backend.repository.OperatorRepository;
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
    private final OperatorRepository operatorRepository;


    public List<TestBench> listAllTestBenches() {
        return testBenchRepository.findAll();
    }

    public TestBench findTestBenchById(String testBenchId) throws NoSuchTestBenchException {

        Optional<TestBench> testBenchOptional = testBenchRepository.findTestBenchByBenchId(testBenchId);

        TestBench testBench;

        if (testBenchOptional.isPresent()) {
            testBench = testBenchOptional.get();
        } else {
            throw new NoSuchTestBenchException(testBenchId);
        }

        return testBench;
    }

    public TestBench addTestBench(TestBenchDTO testBenchToAdd) {
        if (testBenchRepository.existsByBenchId(testBenchToAdd.benchId())) {
            throw new TestBenchAleadyExistException();
        }

        TestBench testBench = new TestBench(
                testBenchToAdd.benchId(),
                testBenchToAdd.name(),
                new ArrayList<>(),
                new ArrayList<>(),
                testBenchToAdd.maintenance(),
                testBenchToAdd.calibration());

        testBenchRepository.save(testBench);

        return testBench;
    }

    public void addMetrologyToTestBench(String testBenchId, String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        TestBench testBench = testBenchRepository
                .findTestBenchByBenchId(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        testBench.metrology().add(metrology);
        testBenchRepository.save(testBench);
    }

    public void removeMetrologyFromTestBench(String testBenchId, String metrologyId) throws NoSuchTestBenchException, NoSuchMetrologyException {
        TestBench testBench = testBenchRepository
                .findTestBenchByBenchId(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        testBench.metrology().remove(metrology);
        testBenchRepository.save(testBench);
    }

    public void addOperatorToTestBench(String testBenchId, String testBenchOperatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorException {

    public void addTestBenchOperatorToTestBench(String testBenchId, String testBenchOperatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorException {
        TestBench testBench = testBenchRepository
                .findTestBenchByBenchId(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));

        Operator operator = operatorRepository
                .findOperatorByOperatorId(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorException(testBenchOperatorId));

        testBench.operator().add(operator);
        operator.testBench().add(testBench);
        operatorRepository.save(operator);
        testBenchRepository.save(testBench);
    }

    public void removeOperatorFromTestBench(String testBenchId, String testBenchOperatorId) throws NoSuchTestBenchException, NoSuchTestBenchOperatorException {
        TestBench testBench = testBenchRepository
                .findTestBenchByBenchId(testBenchId)
                .orElseThrow(() -> new NoSuchTestBenchException(testBenchId));
        Operator operator = operatorRepository
                .findOperatorByOperatorId(testBenchOperatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorException(testBenchOperatorId));
        operator.testBench().remove(testBench);
        testBench.operator().remove(operator);
        testBenchRepository.save(testBench);
        operatorRepository.save(operator);
    }

    public TestBench updateTestBenchById(String benchId, TestBenchDTO testBenchUpdate) throws NoSuchTestBenchException {

        TestBench testBenchToUpdate = testBenchRepository
                .findTestBenchByBenchId(benchId)
                .orElseThrow(() -> new NoSuchTestBenchException(benchId));

        TestBench updatedTestBench = new TestBench(
                benchId,
                testBenchUpdate.name(),
                testBenchToUpdate.metrology(),
                testBenchToUpdate.operator(),
                testBenchUpdate.maintenance(),
                testBenchUpdate.calibration());

        testBenchRepository.save(updatedTestBench);

        return updatedTestBench;
    }

}
