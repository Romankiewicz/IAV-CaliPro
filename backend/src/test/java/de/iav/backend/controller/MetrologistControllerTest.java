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
    private MetrologistRepository metrologistRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

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
        metrologistRepository.save(metrologist);

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
                + expectedMetrologist.metrologistId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("DonkeyKong"))
                .andExpect(jsonPath("$.firstName").value("Donkey"))
                .andExpect(jsonPath("$.lastName").value("Kong"))
                .andExpect(jsonPath("$.eMail").value("DK.notDriftKing@Nintendo.jp"))
                .andExpect(jsonPath("$.role").value("METROLOGIST"));
    }

}
