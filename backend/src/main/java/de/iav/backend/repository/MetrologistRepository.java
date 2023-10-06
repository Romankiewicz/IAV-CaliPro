package de.iav.backend.repository;

import de.iav.backend.model.Metrologist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetrologistRepository extends MongoRepository<Metrologist, String> {
    Metrologist findMetrologistByMetrologistId(String metrologistId);

    Optional<Metrologist> findMetologistByUsername(String username);

    boolean existsByUsername(String username);
}
