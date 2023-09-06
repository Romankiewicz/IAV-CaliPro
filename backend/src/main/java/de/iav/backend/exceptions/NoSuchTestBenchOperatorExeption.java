package de.iav.backend.exceptions;

public class NoSuchTestBenchOperatorExeption extends Exception{

    public NoSuchTestBenchOperatorExeption(String testBenchOperatorId) {
        super("Der Prüfstandsfahrer:\n"
                + testBenchOperatorId
                + "\nexistiert nicht!");
    }
}
