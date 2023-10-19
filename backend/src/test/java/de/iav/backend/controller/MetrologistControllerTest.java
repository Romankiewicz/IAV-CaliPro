package de.iav.backend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Metrologist;
import de.iav.backend.repository.MetrologistRepository;
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

    @Autowired
    private MetrologistRepository metrologistRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/metrologist";

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

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
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

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
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

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist expectedMetrologist = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/"
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

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist expectedMetrologist = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/"
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

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
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

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/"
                                + expectedMetrologis.metrologistId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologistUpdate)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/"
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
    void deleteMetrologist_whenLoggedIn_thenReturnStatusNoContent() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "FarCry",
                "Jack",
                "Carver",
                "jack.carver@crytec.com",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist metrologistToDelete = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrologistToDelete.metrologistId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteMetrologist_whenNotLoggedInAsMetrologist_thenReturnStatusForbidden() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "FarCry",
                "Jack",
                "Carver",
                "jack.carver@crytec.com",
                UserRole.METROLOGIST);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + "/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated())
                .andReturn();

        Metrologist metrologistToDelete = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Metrologist.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrologistToDelete.metrologistId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void deleteMetrologist_whenNotLoggedIn_thenReturnStatusUnauthorized() throws Exception {

        Metrologist metrologist = new Metrologist(
                "1",
                "FarCry",
                "Jack",
                "Carver",
                "jack.carver@crytec.com",
                UserRole.METROLOGIST);

        metrologistRepository.save(metrologist);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + metrologist.metrologistId()))
                .andExpect(status().isUnauthorized());
    }
}
