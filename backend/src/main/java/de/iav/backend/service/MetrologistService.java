package de.iav.backend.service;

import de.iav.backend.exceptions.MetologistAlredyExistException;
import de.iav.backend.exceptions.MetrologistUsernameNotFoundException;
import de.iav.backend.exceptions.NoSuchMetrologistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.model.MetrologistResponse;
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
    private final Argon2Service argon2Service;


    public MetrologistResponse saveMetrologist(MetrologistDTO metrologistToAdd) {

        if (metrologistRepository.existsByUsername(metrologistToAdd.username())) {
            throw new MetologistAlredyExistException();
        }

        Metrologist metrologist = new Metrologist(
                idService.generateId(),
                metrologistToAdd.username(),
                argon2Service.encode(metrologistToAdd.password()),
                metrologistToAdd.firstName(),
                metrologistToAdd.lastName(),
                metrologistToAdd.email(),
                UserRole.METROLOGIST
        );
        metrologistRepository.save(metrologist);

        return new MetrologistResponse(
                metrologist.metrologistId(),
                metrologist.username(),
                metrologist.firstName(),
                metrologist.lastName(),
                metrologist.eMail()
        );
    }


    public Optional<Metrologist> getMetologistById(String metrologistId) {
        return metrologistRepository.findMetrologistByMetrologistId(metrologistId);
    }

    public MetrologistResponse getMetrologistByUsername(String username) {
        Metrologist metrologist = metrologistRepository
                .findMetologistByUsername(username).orElseThrow(MetrologistUsernameNotFoundException::new);

        return new MetrologistResponse(
                metrologist.metrologistId(),
                metrologist.username(),
                metrologist.firstName(),
                metrologist.lastName(),
                metrologist.eMail()
        );
    }


    public MetrologistResponse updateMetrologist(String metrologistId, MetrologistDTO updatedMetrologist) throws  NoSuchMetrologistException {
      metrologistRepository.findById(metrologistId);

      Metrologist metrologist = new Metrologist(
              metrologistId,
              updatedMetrologist.username(),
              argon2Service.encode(updatedMetrologist.password()),
              updatedMetrologist.firstName(),
              updatedMetrologist.lastName(),
              updatedMetrologist.email(),
              UserRole.METROLOGIST
      );

      metrologistRepository.save(metrologist);
      return new MetrologistResponse(
              metrologist.metrologistId(),
              metrologist.username(),
              metrologist.firstName(),
              metrologist.lastName(),
              metrologist.eMail()
      );
    }

    public void deleteMetrologist(String metrologistId) {
        metrologistRepository.deleteById(metrologistId);
    }

}
