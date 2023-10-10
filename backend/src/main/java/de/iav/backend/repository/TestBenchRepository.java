package de.iav.backend.repository;

import de.iav.backend.model.TestBench;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestBenchRepository extends MongoRepository<TestBench, String> {

    Optional<TestBench> findTestBenchById(String testBenchId);

    boolean existsByTestBenchBy(String testBenchId);
}
