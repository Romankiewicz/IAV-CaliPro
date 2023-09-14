package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
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


    public TestBenchOperatorResponse addTestBenchOperator(TestBenchOperatorDTO operatorToAdd){
        if(testBenchOperatorRepository.existsByUsername(operatorToAdd.username())) {
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

//    public TestBenchOperator updateTestBenchOperatorById(String operatorId, TestBenchOperator updatedOperator) throws NoSuchTestBenchOperatorException {
//        testBenchOperatorRepository.findById(operatorId)
//                .orElseThrow(() -> new NoSuchTestBenchOperatorException(operatorId));
//        return testBenchOperatorRepository
//                .save(new TestBenchOperator(
//                        operatorId,
//                        updatedOperator.firstName(),
//                        updatedOperator.lastName(),
//                        updatedOperator.eMail(),
//                        updatedOperator.testBench()
//                ));
//    }

    public void deleteTestBenchOperator(String operatorId) {
        testBenchOperatorRepository.deleteById(operatorId);
    }

}
