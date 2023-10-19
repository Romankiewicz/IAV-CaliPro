package de.iav.backend.exceptions;

public class NoSuchUserException extends RuntimeException{

    public NoSuchUserException() {
        super("User not found!");
    }
}
