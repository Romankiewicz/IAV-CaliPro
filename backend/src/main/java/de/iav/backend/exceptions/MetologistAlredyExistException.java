package de.iav.backend.exceptions;


import org.springframework.dao.DuplicateKeyException;

public class MetologistAlredyExistException extends DuplicateKeyException {

    public MetologistAlredyExistException() {
        super("User already taken!");
    }
}
