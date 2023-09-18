package de.iav.backend.service;

import de.iav.backend.exceptions.TestBenchOperatorAlreadyExistException;
import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.model.TestBenchOperatorDTO;
import de.iav.backend.model.TestBenchOperatorResponse;
import de.iav.backend.repository.TestBenchOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchOperatorService {

    private final TestBenchOperatorRepository testBenchOperatorRepository;
    private final IdService idService;
    private final Argon2Service argon2Service;


    public Optional<TestBenchOperator> getTestBenchOperatorById(String operatorId) {
        return testBenchOperatorRepository.findTestBenchOperatorByOperatorId(operatorId);
    }


    public TestBenchOperatorResponse addTestBenchOperator(TestBenchOperatorDTO operatorToAdd) {
        if (testBenchOperatorRepository.existsByUsername(operatorToAdd.username())) {
            throw new TestBenchOperatorAlreadyExistException();
        }

        TestBenchOperator testBenchOperator = new TestBenchOperator(
                idService.generateId(),
                operatorToAdd.username(),
                argon2Service.encode(operatorToAdd.password()),
                operatorToAdd.firstName(),
                operatorToAdd.lastName(),
                operatorToAdd.eMail(),
                new ArrayList<>()
        );

        testBenchOperatorRepository.save(testBenchOperator);

        return new TestBenchOperatorResponse(
                testBenchOperator.operatorId(),
                testBenchOperator.username(),
                testBenchOperator.firstName(),
                testBenchOperator.lastName(),
                testBenchOperator.eMail(),
                testBenchOperator.testBench()
        );
    }

    public TestBenchOperatorResponse updateTestBenchOperatorById(String operatorId, TestBenchOperatorDTO updatedOperator) {
        testBenchOperatorRepository.findById(operatorId);

        TestBenchOperator testBenchOperator = new TestBenchOperator(
                operatorId,
                updatedOperator.username(),
                argon2Service.encode(updatedOperator.password()),
                updatedOperator.firstName(),
                updatedOperator.lastName(),
                updatedOperator.eMail(),
                testBenchOperatorRepository.findById(operatorId).get().testBench()
        );


        testBenchOperatorRepository.save(testBenchOperator);

        return new TestBenchOperatorResponse(
                testBenchOperator.operatorId(),
                testBenchOperator.username(),
                testBenchOperator.firstName(),
                testBenchOperator.lastName(),
                testBenchOperator.eMail(),
                testBenchOperator.testBench()
        );
    }

    public void deleteTestBenchOperator(String operatorId) {
        testBenchOperatorRepository.deleteById(operatorId);
    }

}
