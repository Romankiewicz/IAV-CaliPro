package de.iav.backend.repository;

import de.iav.backend.model.TestBench;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestBenchRepository extends MongoRepository<TestBench, String> {
}
