package de.iav.backend.repository;

import de.iav.backend.model.TestBenchOperator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TestBenchOperatorRepository extends MongoRepository<TestBenchOperator,String> {

    Optional<TestBenchOperator>findTestBenchOperatorByOperatorId(String operatorId);
    boolean existsByUsername(String username);
}
