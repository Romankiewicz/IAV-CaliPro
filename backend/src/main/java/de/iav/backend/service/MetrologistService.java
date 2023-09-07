package de.iav.backend.service;

import de.iav.backend.model.Metrologist;
import de.iav.backend.repository.MetrologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetrologistService {

    private final MetrologistRepository metrologistRepository;


    public Metrologist addMetrologist(Metrologist metrologistToAdd) {
        return metrologistRepository.save(
                new Metrologist(
                        metrologistToAdd.MetrologistId(),
                        metrologistToAdd.firstName(),
                        metrologistToAdd.lastName(),
                        metrologistToAdd.eMail()
                ));
    }
}
