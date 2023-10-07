package de.iav.backend.controller;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.model.Metrology;
import de.iav.backend.service.MetrologyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrology")
@RequiredArgsConstructor
public class MetrologyController {

    private final MetrologyService metrologyService;

    @GetMapping("/{metrologyId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Metrology getMetrologyById(@PathVariable String metrologyId) throws NoSuchMetrologyException {
        return metrologyService.getMetrologyById(metrologyId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Metrology> listAllMetrology(){
        return metrologyService.listAllMerology();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Metrology addMetrology(@RequestBody Metrology metrologyToAdd) {
        return metrologyService.addMetrology(metrologyToAdd);
    }

    @PutMapping("/{metrologyId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Metrology updateMetrology(@PathVariable String metrologyId, @RequestBody Metrology metrologyToUpdate) throws NoSuchMetrologyException {
        return metrologyService.updateMetology(metrologyId, metrologyToUpdate);
    }


    @DeleteMapping("/{metrologyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMetrology(@PathVariable String metrologyId) {
        metrologyService.deleteMetrology(metrologyId);
    }
}
