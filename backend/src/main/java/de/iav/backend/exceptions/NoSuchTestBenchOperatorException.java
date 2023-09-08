package de.iav.backend.exceptions;

public class NoSuchTestBenchOperatorException extends Exception{

    public NoSuchTestBenchOperatorException(String testBenchOperatorId) {
        super("Der Prüfstandsfahrer:\n"
                + testBenchOperatorId
                + "\nexistiert nicht!");
    }
}
