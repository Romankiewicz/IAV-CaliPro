package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.model.Metrology;
import de.iav.backend.model.MetrologyDTO;
import de.iav.backend.repository.MetrologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetrologyService {

    private final MetrologyRepository metrologyRepository;
    private final IdService idService;

    public Metrology getMetrologyById(String metrologyId) throws NoSuchMetrologyException {
        return metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
    }

    public List<Metrology> listAllMerology() {
        return metrologyRepository.findAll();
    }

    public Metrology addMetrology(MetrologyDTO metrologyToAdd) {
        return metrologyRepository
                .save(
                        new Metrology(
                                idService.generateId(),
                                metrologyToAdd.iavInventory(),
                                metrologyToAdd.manufacturer(),
                                metrologyToAdd.type(),
                                metrologyToAdd.maintenance(),
                                metrologyToAdd.calibration()));
    }

    public Metrology updateMetology(String metrologyId, MetrologyDTO metrologyToUpdate) throws NoSuchMetrologyException {
        getMetrologyById(metrologyId);
        return metrologyRepository
                .save(
                        new Metrology(
                                metrologyId,
                                metrologyToUpdate.iavInventory(),
                                metrologyToUpdate.manufacturer(),
                                metrologyToUpdate.type(),
                                metrologyToUpdate.maintenance(),
                                metrologyToUpdate.calibration()));
    }


    public void deleteMetrology(String metrologyId) {
        metrologyRepository.deleteById(metrologyId);
    }
}
