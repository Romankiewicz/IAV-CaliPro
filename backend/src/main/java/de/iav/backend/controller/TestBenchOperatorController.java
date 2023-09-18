package de.iav.backend.controller;

import de.iav.backend.model.TestBenchOperator;
import de.iav.backend.model.TestBenchOperatorDTO;
import de.iav.backend.model.TestBenchOperatorResponse;
import de.iav.backend.service.TestBenchOperatorService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class TestBenchOperatorController {

    private final TestBenchOperatorService testBenchOperatorService;

    @GetMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<TestBenchOperator> getTestBenchOperatorById(@PathVariable String operatorId) {
        return testBenchOperatorService.getTestBenchOperatorById(operatorId);
    }

    @GetMapping("/me")
    public String helloMe(Principal principal) {
        if (principal != null) {
            return principal.getName();
        }
        return "anonymousUser";
    }
    @PostMapping("/login")
    public Object login() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    @PostMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
        return "anonymousUser";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TestBenchOperatorResponse addTestBenchOperator(@RequestBody TestBenchOperatorDTO testBenchOperatorToAdd) {
        return testBenchOperatorService.addTestBenchOperator(testBenchOperatorToAdd);
    }

    @PutMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.CREATED)
    public TestBenchOperatorResponse updateTestBenchOperator(@PathVariable String operatorId, @RequestBody TestBenchOperator testBenchOperatorToUpdate) {
        return testBenchOperatorService.updateTestBenchOperatorById(operatorId, testBenchOperatorToUpdate);
    }

    @DeleteMapping("/{operatorId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTestBenchOperator(@PathVariable String operatorId) {
        testBenchOperatorService.deleteTestBenchOperator(operatorId);
    }
}


