package de.iav.backend.exceptions;

public class MetrologistUsernameNotFoundException extends RuntimeException {
    public MetrologistUsernameNotFoundException() {
        super("User not found!");
    }
}
