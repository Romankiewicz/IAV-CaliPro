package de.iav.backend.service;

import de.iav.backend.exceptions.MetrologistAlreadyExistException;
import de.iav.backend.exceptions.NoSuchMetrologistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.repository.MetrologistRepository;
import de.iav.backend.security.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MetrologistService {

    private final MetrologistRepository metrologistRepository;
    private final IdService idService;


    public Metrologist addMetrologist(MetrologistDTO metrologistToAdd) {

        if (metrologistRepository.existsByUsername(metrologistToAdd.username())) {
            throw new MetrologistAlreadyExistException();
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


    public Metrologist findMetrologistById(String metrologistId) throws NoSuchMetrologistException {
        return metrologistRepository
                .findMetrologistByMetrologistId(metrologistId)
                .orElseThrow(() -> new NoSuchMetrologistException(metrologistId));
    }

    public Metrologist findMetrologistByUsername(String username) {
        return metrologistRepository
                .findMetologistByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }


    public Metrologist updateMetrologist(String metrologistId, MetrologistDTO metrologistToUpdate) {
        Metrologist metrologist = new Metrologist(
                metrologistId,
                metrologistToUpdate.username(),
                metrologistToUpdate.firstName(),
                metrologistToUpdate.lastName(),
                metrologistToUpdate.email(),
                UserRole.METROLOGIST
        );
        return metrologistRepository.save(metrologist);
    }

    public void deleteMetrologist(String metrologistId) {
        metrologistRepository.deleteById(metrologistId);
    }

}
