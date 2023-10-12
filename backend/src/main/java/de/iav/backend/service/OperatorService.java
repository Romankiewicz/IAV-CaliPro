package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.exceptions.TestBenchOperatorAlreadyExistException;
import de.iav.backend.model.Operator;
import de.iav.backend.model.OperatorDTO;
import de.iav.backend.repository.OperatorRepository;
import de.iav.backend.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OperatorService {

    private final OperatorRepository operatorRepository;
    private final IdService idService;


    public Optional<Operator> findOperatorById(String operatorId) {
        return operatorRepository.findOperatorByOperatorId(operatorId);
    }

    public Optional<Operator> findOperatorByUsername(String username) {
        return operatorRepository.findOperatorByUsername(username);
    }

    public Operator addOperator(OperatorDTO operatorToAdd) {
        if (operatorRepository.existsByUsername(operatorToAdd.username())) {
            throw new TestBenchOperatorAlreadyExistException();
        }

        Operator operator = new Operator(
                idService.generateId(),
                operatorToAdd.username(),
                operatorToAdd.firstName(),
                operatorToAdd.lastName(),
                operatorToAdd.eMail(),
                new ArrayList<>(),
                UserRole.OPERATOR
        );

        operatorRepository.save(operator);

        return operator;
    }

    public Operator updateOperatorById(String operatorId, OperatorDTO updatedOperator) throws NoSuchTestBenchOperatorException {

        Optional<Operator> operatorToUpdate = operatorRepository.findOperatorByOperatorId(operatorId);

        if (operatorToUpdate.isEmpty()) {
            throw new NoSuchTestBenchOperatorException(operatorId);
        }

        Operator operator = new Operator(
                operatorId,
                updatedOperator.username(),
                updatedOperator.firstName(),
                updatedOperator.lastName(),
                updatedOperator.eMail(),
                operatorToUpdate.get().testBench(),
                UserRole.OPERATOR

        );


        operatorRepository.save(operator);

        return operator;
    }

    public void deleteOperator(String operatorId) {
        operatorRepository.deleteById(operatorId);
    }

}
