package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchMetrologistException;
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
                )
        );
    }

    public Metrologist getMetrologistById(String metrologistId) throws NoSuchMetrologistException {
        return metrologistRepository
                .findById(metrologistId)
                .orElseThrow(()->new NoSuchMetrologistException(metrologistId));
    }


    public Metrologist updateMetrologist(String metrologistId, Metrologist metrologistToUpdate) throws NoSuchMetrologistException {
        getMetrologistById(metrologistId);
        return metrologistRepository.save(
                new Metrologist(
                        metrologistId,
                        metrologistToUpdate.firstName(),
                        metrologistToUpdate.lastName(),
                        metrologistToUpdate.eMail()
                )
        );
    }

    public void deleteMetrologist(String metrologistId){
        metrologistRepository.deleteById(metrologistId);
    }

}
