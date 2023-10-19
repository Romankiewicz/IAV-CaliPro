package de.iav.frontend.service;


import java.security.SecureRandom;

public class IdService {

    private static IdService instance;
    private final SecureRandom random =new SecureRandom();

    public static synchronized IdService getInstance() {
        if (instance == null) {
            instance = new IdService();
        }
        return instance;
    }

    public String generateRandomId() {
        int min = 10000;
        int max = 99999;
        int randomNum = this.random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNum);
    }
}
