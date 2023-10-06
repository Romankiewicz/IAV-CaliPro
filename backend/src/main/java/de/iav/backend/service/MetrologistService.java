package de.iav.backend.service;

import de.iav.backend.exceptions.MetologistAlredyExistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.repository.MetrologistRepository;
import de.iav.backend.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetrologistService {

    private final MetrologistRepository metrologistRepository;
    private final IdService idService;


    public Metrologist addMetrologist(MetrologistDTO metrologistToAdd) {

        if (metrologistRepository.existsByUsername(metrologistToAdd.username())) {
            throw new MetologistAlredyExistException();
        }

        Metrologist metrologist = new Metrologist(
                idService.generateId(),
                metrologistToAdd.username(),
                metrologistToAdd.firstName(),
                metrologistToAdd.lastName(),
                metrologistToAdd.email(),
                UserRole.METROLOGIST
        );
        metrologistRepository.save(metrologist);

        return metrologist;
    }


    public Optional<Metrologist> findMetrologistById(String metrologistId) {
        return metrologistRepository.findMetrologistByMetrologistId(metrologistId);
    }

    public Optional<Metrologist> findMetrologistByUsername(String username) {
        return metrologistRepository.findMetologistByUsername(username);
    }


    public Metrologist updateMetrologist(String metrologistId, Metrologist metrologistToUpdate) {
        Metrologist metrologist = new Metrologist(
                metrologistId,
                metrologistToUpdate.username(),
                metrologistToUpdate.firstName(),
                metrologistToUpdate.lastName(),
                metrologistToUpdate.eMail(),
                metrologistToUpdate.role()
        );
        return metrologistRepository.save(metrologist);
    }

    public void deleteMetrologist(String metrologistId) {
        metrologistRepository.deleteById(metrologistId);
    }

}
