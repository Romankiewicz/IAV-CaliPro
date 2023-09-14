package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.repository.TestBenchOperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchOperatorService {

    private final TestBenchOperatorRepository testBenchOperatorRepository;


    public Optional<TestBenchOperator> getTestBenchOperatorById(String operatorId) {
        return testBenchOperatorRepository.findTestBenchOperatorByOperatorId(operatorId);
    }
//      Old Method => PLEASE DELETE AFTER INCLUDE NEW METHOD!!!
//    public TestBenchOperator addTestBenchOperator(TestBenchOperator newOperator) {
//        return testBenchOperatorRepository.save(new TestBenchOperator(
//                newOperator.operatorId(),
//                newOperator.firstName(),
//                newOperator.lastName(),
//                newOperator.eMail(),
//                new ArrayList<>()
//        ));
//    }

    public TestBenchOperatorResponse
    public FliprUserResponse saveFliprUser(FliprUserDTO userToSave) {
        if (fliprUserRepo.existsByUsername(userToSave.username())) {
            throw new FliprUserAlreadyExistException();
        }

        FliprUser fliprUser = new FliprUser(
                idService.generateId(),
                userToSave.username(),
                argon2Service.encode(userToSave.password()),
                new ArrayList<>(),
                new ArrayList<>()
        );

        fliprUserRepo.save(fliprUser);

        return new FliprUserResponse(
                fliprUser.id(),
                fliprUser.username(),
                fliprUser.fliprs(),
                fliprUser.likedFliprs()
        );
    }


    public TestBenchOperator updateTestBenchOperatorById(String operatorId, TestBenchOperator updatedOperator) throws NoSuchTestBenchOperatorException {
        testBenchOperatorRepository.findById(operatorId)
                .orElseThrow(() -> new NoSuchTestBenchOperatorException(operatorId));
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
