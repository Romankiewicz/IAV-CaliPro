package de.iav.backend.exceptions;


import org.springframework.dao.DuplicateKeyException;

public class MetrologistAlreadyExistException extends DuplicateKeyException {

    public MetrologistAlreadyExistException() {
        super("User already taken!");
    }
}
