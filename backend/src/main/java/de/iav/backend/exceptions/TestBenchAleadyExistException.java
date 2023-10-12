package de.iav.backend.exceptions;

import org.springframework.dao.DuplicateKeyException;

public class TestBenchAleadyExistException  extends DuplicateKeyException {

    public TestBenchAleadyExistException() {
        super ("This is an already existing TestBench!!!");
    }
}
