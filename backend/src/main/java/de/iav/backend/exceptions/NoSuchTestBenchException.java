package de.iav.backend.exceptions;

public class NoSuchTestBenchException extends Exception {

    public NoSuchTestBenchException(String testBenchId) {
        super("Der Prüfstand:\n"
                + testBenchId
                + "\nexistiert nicht!");
    }

}
