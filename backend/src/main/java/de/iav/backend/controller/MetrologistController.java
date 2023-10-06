package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.service.MetrologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/metrologist")
@RequiredArgsConstructor
public class MetrologistController {

    private final MetrologistService metrologistService;


    @GetMapping("/id/{metrologistId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Metrologist findMetrologistById(@PathVariable String metrologistId) throws NoSuchMetrologistException {
        return metrologistService.findMetrologistById(metrologistId);
    }

    @GetMapping("/{username}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Metrologist findMetrologistByUsername(@PathVariable String username) {
        return metrologistService.findMetrologistByUsername(username);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Metrologist addMetrologist(@RequestBody MetrologistDTO metrologistToAdd) {
        return metrologistService.addMetrologist(metrologistToAdd);
    }

    @PutMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Metrologist updateMetrologist(@PathVariable String metrologistId, @RequestBody MetrologistDTO metrologistToUpdate) {
        return metrologistService.updateMetrologist(metrologistId, metrologistToUpdate);
    }

    @DeleteMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMetrologist(@PathVariable String metrologistId) {
        metrologistService.deleteMetrologist(metrologistId);
    }

}
