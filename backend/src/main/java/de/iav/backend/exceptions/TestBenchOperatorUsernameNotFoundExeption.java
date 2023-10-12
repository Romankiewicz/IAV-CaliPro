package de.iav.backend.exceptions;

public class TestBenchOperatorUsernameNotFoundExeption extends RuntimeException {

    public TestBenchOperatorUsernameNotFoundExeption() {
        super ("User not found!");
    }
}
