package de.iav.backend.repository;

import de.iav.backend.model.Metrology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetrologyRepository extends MongoRepository<Metrology, String> {
}
