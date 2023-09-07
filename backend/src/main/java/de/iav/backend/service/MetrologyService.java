package de.iav.backend.service;

import de.iav.backend.model.Metrology;
import de.iav.backend.repository.MetrologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetrologyService {

    private final MetrologyRepository metrologyRepository;

    public Metrology addMetrology(Metrology metrologyToAdd) {
        return metrologyRepository.save(new Metrology(
                metrologyToAdd.metrologyId(),
                metrologyToAdd.iavInventory(),
                metrologyToAdd.manufacturer(),
                metrologyToAdd.type(),
                metrologyToAdd.maintenance(),
                metrologyToAdd.calibration()));
    }
}
