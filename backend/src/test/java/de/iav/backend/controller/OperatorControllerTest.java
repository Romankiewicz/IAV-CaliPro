package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Operator;
import de.iav.backend.security.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DirtiesContext
    @WithMockUser
    void addOperator_whenLoggedIn_thenReturnAddedOperator() throws Exception {

        Operator operator = new Operator(
                "1",
                "Jackass",
                "Johnny",
                "Knoxville",
                "johnny.knoxville@jackass.com",
                new ArrayList<>(),
                UserRole.OPERATOR);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/operators")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated());
    }
}
