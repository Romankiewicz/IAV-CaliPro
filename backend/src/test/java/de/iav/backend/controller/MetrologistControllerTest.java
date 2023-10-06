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

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
                UserRole.METROLOGIST
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/metrologist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/metrologist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].teacherId").exists())
                .andExpect(jsonPath("$[0].loginName").value("FordProbe"))
                .andExpect(jsonPath("$[0].firstName").value("Dirk"))
                .andExpect(jsonPath("$[0].lastName").value("Stadge"))
                .andExpect(jsonPath("$[0].email").value("dirk@gmx.de"))
                .andExpect(jsonPath("$[0].courses.length()").value(0));
    }


    @Test
    @DirtiesContext
    @WithMockUser
    void findMetrologistById_whenMetrologistExists_thenReturnMetrologist() throws Exception {
        Metrologist metrologist = new Metrologist(
                "1",
                "DonkeyKong",
                "Donkey",
                "Kong",
                "DK.notDriftKing@Nintendo.jp",
                UserRole.METROLOGIST
        );

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

}
