package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Metrology;
import de.iav.backend.repository.MetrologyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MetrologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetrologyRepository metrologyRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/metrology";



    @Test
    @DirtiesContext
    @WithMockUser
    void getMetrologyById_whenMetrologyExists_thenReturnMetrology() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20)
        );

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/"
                + metrology.metrologyId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.iavInventory").value("1"))
                .andExpect(jsonPath("$.manufacturer").value("Horiba"))
                .andExpect(jsonPath("$.type").value("MEXA"))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20"));
    }

    @Test
    @WithMockUser
    void listAllMetrology_whenListIsEmpty_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(content().json("[]"));
    }

    @Test
    void addMetrology() {
    }

    @Test
    void updateMetrology() {
    }

    @Test
    void deleteMetrology() {
    }
}
