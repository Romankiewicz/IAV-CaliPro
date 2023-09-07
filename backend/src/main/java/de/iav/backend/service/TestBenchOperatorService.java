package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorExeption;
import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.repository.TestBenchOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchOperatorService {

    private final TestBenchOperatorRepository testBenchOperatorRepository;


    public Optional<TestBenchOperator> getTestBenchOperatorById(String operatorId) {
        return testBenchOperatorRepository.findById(operatorId);
    }

    public TestBenchOperator addTestBenchOperator(TestBenchOperator newOperator) {
        return testBenchOperatorRepository.save(new TestBenchOperator(
                newOperator.operatorId(),
                newOperator.firstName(),
                newOperator.lastName(),
                newOperator.eMail(),
                new ArrayList<>()
        ));
    }

    public TestBenchOperator updateTestBenchOperatorById(String operatorId, TestBenchOperator updatedOperator) throws NoSuchTestBenchOperatorExeption {
        testBenchOperatorRepository.findById(operatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorExeption(operatorId));
        return testBenchOperatorRepository
                .save(new TestBenchOperator(
                        operatorId,
                        updatedOperator.firstName(),
                        updatedOperator.lastName(),
                        updatedOperator.eMail(),
                        updatedOperator.testBench()
                ));
    }

    public void deleteTestBenchOperator(String operatorId) {
        testBenchOperatorRepository.deleteById(operatorId);
    }

}
