package de.iav.backend.service;

import de.iav.backend.exceptions.MetologistAlredyExistException;
import de.iav.backend.model.Metrologist;
import de.iav.backend.model.MetrologistDTO;
import de.iav.backend.model.MetrologistResponse;
import de.iav.backend.repository.MetrologistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MetrologistService {

    private final MetrologistRepository metrologistRepository;
    private final IdService idService;
    private final Argon2Service argon2Service;


    public MetrologistResponse addMetrologist(MetrologistDTO metrologistToAdd) {

        if (metrologistRepository.existsByUsername(metrologistToAdd.username())) {
            throw new MetologistAlredyExistException();
        }

        Metrologist metrologist = new Metrologist(
                idService.generateId(),
                metrologistToAdd.username(),
                argon2Service.encode(metrologistToAdd.password()),
                metrologistToAdd.firstName(),
                metrologistToAdd.lastName(),
                metrologistToAdd.email()
        );
        metrologistRepository.save(metrologist);

        return new MetrologistResponse(
                metrologist.username(),
                metrologist.firstName(),
                metrologist.lastName(),
                metrologist.eMail()
        );
    }


    public Optional<Metrologist> getMetologistById(String metrologistId) {
        return metrologistRepository.findMetrologistByMetrologistId(metrologistId);
    }


//    public Metrologist updateMetrologist(String metrologistId, Metrologist metrologistToUpdate) throws NoSuchMetrologistException {
//        getMetrologistById(metrologistId);
//        return metrologistRepository.save(
//                new Metrologist(
//                        metrologistId,
//                        metrologistToUpdate.firstName(),
//                        metrologistToUpdate.lastName(),
//                        metrologistToUpdate.eMail()
//                )
//        );
//    }

    public void deleteMetrologist(String metrologistId) {
        metrologistRepository.deleteById(metrologistId);
    }

}
