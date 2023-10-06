package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.model.Operator;
import de.iav.backend.model.OperatorDTO;
import de.iav.backend.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/id/{operatorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Operator> findOperatorById(@PathVariable String operatorId) {
        return operatorService.findOperatorById(operatorId);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Operator> findOperatorByUsername(@PathVariable String username) {
        return operatorService.findOperatorByUsername(username);
    }


    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public Operator addOperator(@RequestBody OperatorDTO testBenchOperatorToAdd) {
        return operatorService.addOperator(testBenchOperatorToAdd);
    }

    @PutMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Operator updateOperator(@PathVariable String operatorId, @RequestBody OperatorDTO testBenchOperatorToUpdate) throws NoSuchTestBenchOperatorException {
        return operatorService.updateOperatorById(operatorId, testBenchOperatorToUpdate);
    }

    @DeleteMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOperator(@PathVariable String operatorId) {
        operatorService.deleteOperator(operatorId);
    }
}


