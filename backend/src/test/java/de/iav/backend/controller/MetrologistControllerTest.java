package de.iav.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Metrologist;
import de.iav.backend.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MetrologistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @DirtiesContext
    @WithMockUser
    void addMetrologist_whenLoggedIn_thenReturnAddedMetrologist() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "MasterChief",
                "John",
                "117",
                "master.chief@activision.com",
                UserRole.METROLOGIST);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void addMetrologist_whenNotLoggedIn_thenGetRejected() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "MasterChief",
                "John",
                "117",
                "master.chief@activision.com",
                UserRole.METROLOGIST);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void findMetrologistById_whenMetrologistExists_thenGetStatusIsCreated() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "DonkeyKong",
                "Donkey",
                "Kong",
                "DK.notDriftKing@Nintendo.jp",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist expectedMetrologist = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/metrologist/id/"
                        + expectedMetrologist.metrologistId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("DonkeyKong"))
                .andExpect(jsonPath("$.firstName").value("Donkey"))
                .andExpect(jsonPath("$.lastName").value("Kong"))
                .andExpect(jsonPath("$.email").value("DK.notDriftKing@Nintendo.jp"))
                .andExpect(jsonPath("$.role").value("METROLOGIST"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void findMetrologistByUsername_whenMetrologistExists_thenReturnMetrologist() throws Exception {
        Metrologist metrologist = new Metrologist(
                "1",
                "DonkeyKong",
                "Donkey",
                "Kong",
                "DK.notDriftKing@Nintendo.jp",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist expectedMetrologist = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/metrologist/"
                        + expectedMetrologist.username()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("DonkeyKong"))
                .andExpect(jsonPath("$.firstName").value("Donkey"))
                .andExpect(jsonPath("$.lastName").value("Kong"))
                .andExpect(jsonPath("$.email").value("DK.notDriftKing@Nintendo.jp"))
                .andExpect(jsonPath("$.role").value("METROLOGIST"));
    }


    @Test
    @DirtiesContext
    @WithMockUser
    void updateMetrologist_whenLoggedIn_thenReturnUpdatedMetrologist() throws Exception {
        Metrologist metrologist = new Metrologist(
                "1",
                "QuakeModder",
                "Gordon",
                "Freeman",
                "g.freeman@blackmesa.com",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist expectedMetrologis = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        Metrologist metrologistUpdate = new Metrologist(
                "???",
                "QuakeModder",
                "gordon",
                "freeman",
                "g.freeman@blackmesa.com",
                UserRole.METROLOGIST);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/metrologist/"
                                + expectedMetrologis.metrologistId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologistUpdate)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/metrologist/id/"
                        + expectedMetrologis.metrologistId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("QuakeModder"))
                .andExpect(jsonPath("$.firstName").value("gordon"))
                .andExpect(jsonPath("$.lastName").value("freeman"))
                .andExpect(jsonPath("$.email").value("g.freeman@blackmesa.com"))
                .andExpect(jsonPath("$.role").value("METROLOGIST"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void deleteMetrologist_whenMetrologistExists_thenReturnNothing() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "FarCry",
                "Jack",
                "Carver",
                "jack.carver@crytec.com",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist metrologistToDelete = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/metrologist/"
                        + metrologistToDelete.metrologistId()))
                .andExpect(status().isNoContent());
    }
}
