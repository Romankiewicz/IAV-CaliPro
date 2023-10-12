package de.iav.backend.exceptions;

import java.util.NoSuchElementException;

public class NoSuchMetrologistException extends Exception {
    public NoSuchMetrologistException(String metrologistId){
        super("Der Messtechniker:\n"
                + metrologistId
                + "\nexistiert nicht!");
    }
}
