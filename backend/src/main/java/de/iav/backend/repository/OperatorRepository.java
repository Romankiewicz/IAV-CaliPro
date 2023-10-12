package de.iav.backend.repository;

import de.iav.backend.model.Operator;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OperatorRepository extends MongoRepository<Operator,String> {

    Optional<Operator>findOperatorByOperatorId(String operatorId);
    Optional<Operator> findOperatorByUsername(String username);
    boolean existsByUsername(String username);
}

