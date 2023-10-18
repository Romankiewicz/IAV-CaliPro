package de.iav.backend.controller;

import de.iav.backend.model.Metrology;
import de.iav.backend.repository.MetrologyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    @DirtiesContext
    @WithMockUser
    void listAllMetrology_whenMetrologyExist_thenReturnMetrologyList() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].metrologyId").value("1"))
                .andExpect(jsonPath("$[0].iavInventory").value("1"))
                .andExpect(jsonPath("$[0].manufacturer").value("Horiba"))
                .andExpect(jsonPath("$[0].type").value("MEXA"))
                .andExpect(jsonPath("$[0].maintenance").value("2022-02-20"))
                .andExpect(jsonPath("$[0].calibration").value("2022-02-20"));
    }

    @Test
    @DirtiesContext
    void addMetrology_whenNotLoggedIn_thenGetRejected() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                            {
                            "metrologId": "1",
                            "iavInventory":  "1",
                            "manufacturer": "Horiba",
                            "type": "MEXA",
                            "maintenance": "2022-02-20",
                            "calibration": "2022-02-20"
                            }
                        """
                ))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void addMetrology_whenLoggedIn_thenGetStatusIsCreated() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                    {
                                    "metrologId": "1",
                                    "iavInventory":  "1",
                                    "manufacturer": "Horiba",
                                    "type": "MEXA",
                                    "maintenance": "2022-02-20",
                                    "calibration": "2022-02-20"
                                    }
                                """
                        ))
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateMetrology_whenNotLoggedIn_thenGetRejected() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/"
                                + metrology.metrologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                    {
                                    "metrologId": "1",
                                    "iavInventory":  "1",
                                    "manufacturer": "Horiba",
                                    "type": "MEXA",
                                    "maintenance": "2023-02-20",
                                    "calibration": "2023-02-20"
                                    }
                                """
                        ))
                        .andExpect(status()
                        .isForbidden());
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void updateMetrology_whenLoggedIn_thenReturnUpdatedMetrology() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);


        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/"
                        + metrology.metrologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                    {
                                    "metrologId": "1",
                                    "iavInventory":  "1",
                                    "manufacturer": "Horiba",
                                    "type": "MEXA",
                                    "maintenance": "2023-02-20",
                                    "calibration": "2023-02-20"
                                    }
                                """
                                ))
                        .andExpect(status()
                        .isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.metrologyId").value("1"))
                .andExpect(jsonPath("$.iavInventory").value("1"))
                .andExpect(jsonPath("$.manufacturer").value("Horiba"))
                .andExpect(jsonPath("$.type").value("MEXA"))
                .andExpect(jsonPath("$.maintenance").value("2023-02-20"))
                .andExpect(jsonPath("$.calibration").value("2023-02-20"));

    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void deleteMetrology_whenLoggedIn_thenReturnStatusNoContent() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteMetrology_whenNotLoggedInAsMetrologist_thenReturnStatusForbidden() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void deleteMetrology_whenNotLoggedInAsMetrologist_thenReturnStatusUnauthorized() throws Exception {

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20));

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isUnauthorized());
    }
}
