package de.iav.backend.security;

import de.iav.backend.model.Metrology;
import de.iav.backend.model.TestBench;
import de.iav.backend.repository.MetrologyRepository;
import de.iav.backend.repository.TestBenchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestBenchService {

    private final TestBenchRepository testBenchRepository;
    private final MetrologyRepository metrologyRepository;


    public List<TestBench> getAllTestBenches() {
        return testBenchRepository.findAll();
    }

    public Optional<TestBench> getTestbenchById(String testBenchId) {
        return testBenchRepository.findById(testBenchId);
    }

    public void addMetrologyToTestBench(String testBenchId, String metrologyId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchElementException("Das Messgerät\n"
                        + metrologyId
                        + "\nexistiert nicht"));
        testBench.metrology().add(metrology);
        testBenchRepository.save(testBench);
    }

    public void removeMetrologyFromTestBench(String testBenchId, String metrologyId) {
        TestBench testBench = testBenchRepository
                .findById(testBenchId)
                .orElseThrow(() -> new NoSuchElementException("Der Prüfstand\n"
                        + testBenchId
                        + "\nexistiert nicht!"));
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchElementException("Das Messgerät\n"
                        + metrologyId
                        + "\nexistier nicht!"));
        testBench.metrology().remove(metrology);
        testBenchRepository.save(testBench);
    }
}
