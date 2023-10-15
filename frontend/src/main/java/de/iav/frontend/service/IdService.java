package de.iav.frontend.service;


import java.util.Random;

public class IdService {

    private static IdService instance;

    public static synchronized IdService getInstance() {
        if (instance == null) {
            instance = new IdService();
        }
        return instance;
    }

    public String generateRandomId() {
        Random random = new Random();
        int min = 10000;
        int max = 99999;
        int randomNum = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNum);
    }
}
