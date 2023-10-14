package de.iav.frontend.service;

public class TestBenchService {

    private static TestBenchService instance;



    public TestBenchService() {

    }

    public TestBenchService getInstance() {

        if (instance == null) {
            instance = new TestBenchService();
        }
        return instance;
    }
}
