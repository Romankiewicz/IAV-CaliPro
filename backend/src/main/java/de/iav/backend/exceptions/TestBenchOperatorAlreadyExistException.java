package de.iav.backend.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class TestBenchOperatorAlreadyExistException extends DuplicateKeyException {

    public TestBenchOperatorAlreadyExistException() {
        super("User already taken!");
    }
}
