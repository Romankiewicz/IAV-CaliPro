package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchTestBenchOperatorException;
import de.iav.backend.model.Operator;
import de.iav.backend.model.OperatorDTO;
import de.iav.backend.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/operator")
@RequiredArgsConstructor
public class OperatorController {

    private final OperatorService operatorService;

    @GetMapping("/id/{operatorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Operator findOperatorById(@PathVariable String operatorId) throws NoSuchTestBenchOperatorException {
        return operatorService.findOperatorById(operatorId);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Operator findOperatorByUsername(@PathVariable String username) {
        return operatorService.findOperatorByUsername(username);
    }


    @PostMapping
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


