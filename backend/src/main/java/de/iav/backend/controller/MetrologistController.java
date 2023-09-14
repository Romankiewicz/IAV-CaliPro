package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.service.MetrologistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController("/api/metrologist")
@RequiredArgsConstructor
public class MetrologistController {

    private final MetrologistService metrologistService;

    @GetMapping("/{metrologistId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Metrologist findMetrologistById(@PathVariable String metrologistId) throws NoSuchMetrologistException {
        return metrologistService.getMetrologistById(metrologistId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Metrologist addMetrologist(@RequestBody Metrologist metrologistToAdd) {
        return metrologistService.addMetrologist(metrologistToAdd);
    }


}
