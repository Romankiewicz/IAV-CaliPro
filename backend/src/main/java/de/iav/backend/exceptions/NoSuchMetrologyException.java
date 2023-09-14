package de.iav.backend.exceptions;

public class NoSuchMetrologyException extends Exception{

    public NoSuchMetrologyException(String metrologyId){
        super("Das Messgerät:\n"
                + metrologyId
                + "\nexistiert nicht!");
    }
}
