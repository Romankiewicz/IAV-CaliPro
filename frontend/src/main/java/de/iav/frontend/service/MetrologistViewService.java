package de.iav.frontend.service;

import de.iav.frontend.model.Metrology;
import de.iav.frontend.model.TestBench;

import java.util.List;

public class MetrologistViewService {

    private static MetrologistViewService instance;
    private final TestBenchService testBenchService = TestBenchService.getInstance();
    private final MetrologyService metrologyService = MetrologyService.getInstance();


    public static synchronized MetrologistViewService getInstance() {
        if (instance == null) {
            instance = new MetrologistViewService();
        }
        return instance;
    }

    public List<Metrology> getAllMetrologies() {

        return metrologyService.getAllMetrologies();
    }

    public List<Metrology> getMetrologyByMaintenanceOrCalibrationDue() {
        return metrologyService.getMetrologyByMaintenanceOrCalibrationDue();
    }

    public void deleteMetrology(String metrologyId) {
        metrologyService.deleteMetrology(metrologyId);
    }

    public List<TestBench> getAllTestBenches() {

        return testBenchService.getAllTestBenches();
    }

    public List<TestBench> getTestBenchesByMaintenanceOrCalibrationDue() {
        return testBenchService.getTestBenchesByMaintenanceOrCalibrationDue();
    }
}
