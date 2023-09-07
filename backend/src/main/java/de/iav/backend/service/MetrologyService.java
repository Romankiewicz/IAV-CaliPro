package de.iav.backend.service;

import de.iav.backend.exceptions.NoSuchMetrologyException;
import de.iav.backend.exceptions.NoSuchTestBenchException;
import de.iav.backend.model.Metrology;
import de.iav.backend.model.TestBench;
import de.iav.backend.repository.MetrologyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetrologyService {

    private final MetrologyRepository metrologyRepository;

    public Metrology findMetrologyById(String metrologyId) throws NoSuchMetrologyException {
        return metrologyRepository
                .findById(metrologyId)
                .orElseThrow(()->new NoSuchMetrologyException(metrologyId));
    }

    public List<Metrology> listAllMerology(){
        return metrologyRepository.findAll();
    }

    public Metrology addMetrology(Metrology metrologyToAdd) {
        return metrologyRepository
                .save(
                        new Metrology(
                                metrologyToAdd.metrologyId(),
                                metrologyToAdd.iavInventory(),
                                metrologyToAdd.manufacturer(),
                                metrologyToAdd.type(),
                                metrologyToAdd.maintenance(),
                                metrologyToAdd.calibration()));
    }

    public Metrology updateMetology(String metrologyId, Metrology metrologyToUpdate) throws NoSuchMetrologyException {
        findMetrologyById(metrologyId);
        return metrologyRepository
                .save(
                        new Metrology(
                                metrologyId,
                                metrologyToUpdate.iavInventory(),
                                metrologyToUpdate.manufacturer(),
                                metrologyToUpdate.type(),
                                metrologyToUpdate.maintenance(),
                                metrologyToUpdate.calibration()));
    }

    public Metrology setMetrologymaintenanceDate(String metrologyId, int date, int month, int year) throws NoSuchMetrologyException {
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        metrology.maintenance().setDate(date);
        metrology.maintenance().setMonth(month);
        metrology.maintenance().setYear(year);
        metrologyRepository.save(metrology);
        return metrology;
    }

    public Metrology setMetrologyCalibrationDate(String metrologyId, int date, int month, int year) throws NoSuchMetrologyException {
        Metrology metrology = metrologyRepository
                .findById(metrologyId)
                .orElseThrow(() -> new NoSuchMetrologyException(metrologyId));
        metrology.calibration().setDate(date);
        metrology.calibration().setMonth(month);
        metrology.calibration().setYear(year);
        metrologyRepository.save(metrology);
        return metrology;
    }

    public void deleteMetrology(String metrologyId){
        metrologyRepository.deleteById(metrologyId);
    }
}
