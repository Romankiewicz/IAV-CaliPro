package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.model.MetrologistResponse;
import de.iav.backend.service.MetrologistService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/metrologist")
public class MetrologistController {

    private final MetrologistService metrologistService;

    public MetrologistController(MetrologistService metrologistService) {
        this.metrologistService = metrologistService;
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

    @GetMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Optional<Metrologist> findMetrologistById(@PathVariable String metrologistId) {
        return metrologistService.getMetologistById(metrologistId);
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MetrologistResponse saveMetrologist(@RequestBody MetrologistDTO metrologistToAdd) {
        return metrologistService.saveMetrologist(metrologistToAdd);
    }

    @PutMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.CREATED)
    public MetrologistResponse updateMetrologist(@PathVariable String metrologistId, @RequestBody MetrologistDTO updatedMetrologist) throws NoSuchMetrologistException {
        return metrologistService.updateMetrologist(metrologistId, updatedMetrologist);
    }

    @DeleteMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMetrologist(@PathVariable String metrologistId) {
        metrologistService.deleteMetrologist(metrologistId);
    }

}
