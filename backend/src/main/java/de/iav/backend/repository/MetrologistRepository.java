package de.iav.backend.repository;

import de.iav.backend.model.Metrologist;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetrologistRepository extends MongoRepository<Metrologist, String> {

}
