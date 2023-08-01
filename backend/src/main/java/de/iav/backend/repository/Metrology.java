package de.iav.backend.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Metrology extends MongoRepository<Metrology, String> {
}
