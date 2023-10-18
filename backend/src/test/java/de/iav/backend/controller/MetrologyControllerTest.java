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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MetrologyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MetrologyRepository metrologyRepository;

    private final String BASE_URL = "/api/metrology";

    private Date getTestDate() {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd");
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return dateFormat.parse("2022-02-20");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void getMetrologyById_whenMetrologyExists_thenReturnMetrology() throws Exception {

        Date date = getTestDate();

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date
        );

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.iavInventory").value("1"))
                .andExpect(jsonPath("$.manufacturer").value("Horiba"))
                .andExpect(jsonPath("$.type").value("MEXA"))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));
    }

    @Test
    @WithMockUser(roles = "METROLOGIST")
    void listAllMetrology_whenListIsEmpty_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(content().json("[]"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void listAllMetrology_whenMetrologyExist_thenReturnMetrologyList() throws Exception {

        Date date = getTestDate();

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].metrologyId").value("1"))
                .andExpect(jsonPath("$[0].iavInventory").value("1"))
                .andExpect(jsonPath("$[0].manufacturer").value("Horiba"))
                .andExpect(jsonPath("$[0].type").value("MEXA"))
                .andExpect(jsonPath("$[0].maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].calibration").value("2022-02-20T00:00:00.000+00:00"));
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

        Date date = getTestDate();
        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

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

        Date date = getTestDate();

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

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
                .andExpect(jsonPath("$.maintenance").value("2023-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2023-02-20T00:00:00.000+00:00"));

    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void deleteMetrology_whenLoggedIn_thenReturnStatusNoContent() throws Exception {

        Date date = getTestDate();
        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteMetrology_whenNotLoggedInAsMetrologist_thenReturnStatusForbidden() throws Exception {

        Date date = getTestDate();
        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void deleteMetrology_whenNotLoggedInAsMetrologist_thenReturnStatusUnauthorized() throws Exception {

        Date date = getTestDate();
        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date);

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrology.metrologyId()))
                .andExpect(status().isUnauthorized());
    }
}
