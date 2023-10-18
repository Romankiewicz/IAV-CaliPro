package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.Operator;
import de.iav.backend.repository.OperatorRepository;
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

import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OperatorRepository operatorRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/operator";

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
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    void addOperator_whenNotLoggedIn_thenGetStatusIsCreated() throws Exception {

        Operator operator = new Operator(
                "1",
                "Jackass",
                "Johnny",
                "Knoxville",
                "johnny.knoxville@jackass.com",
                new ArrayList<>(),
                UserRole.OPERATOR);
        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void findOperatorById_whenOperatorExists_thenReturnOperator() throws Exception {

        Operator operator = new Operator(
                "1",
                "Jackass",
                "Johnny",
                "Knoxville",
                "johnny.knoxville@jackass.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated())
                .andReturn();

        Operator expectedOperator = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Operator.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/"
                        + expectedOperator.operatorId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("Jackass"))
                .andExpect(jsonPath("$.firstName").value("Johnny"))
                .andExpect(jsonPath("$.lastName").value("Knoxville"))
                .andExpect(jsonPath("$.email").value("johnny.knoxville@jackass.com"))
                .andExpect(jsonPath("$.testBench.length()").value(0))
                .andExpect(jsonPath("$.role").value("OPERATOR"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void findOperatorByUsername_whenOperatorExists_thenReturnOperator() throws Exception {

        Operator operator = new Operator(
                "1",
                "Jackass",
                "Johnny",
                "Knoxville",
                "johnny.knoxville@jackass.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated())
                .andReturn();

        Operator expectedOperator = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Operator.class);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/"
                        + expectedOperator.username()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("Jackass"))
                .andExpect(jsonPath("$.firstName").value("Johnny"))
                .andExpect(jsonPath("$.lastName").value("Knoxville"))
                .andExpect(jsonPath("$.email").value("johnny.knoxville@jackass.com"))
                .andExpect(jsonPath("$.testBench.length()").value(0))
                .andExpect(jsonPath("$.role").value("OPERATOR"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateOperator_whenLoggedIn_thenReturnUpdatedOperator() throws Exception {

        Operator operator = new Operator(
                "1",
                "Kampfkollos",
                "Eric",
                "Cartman",
                "eric.catrman@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated())
                .andReturn();

        Operator expectedOperator = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Operator.class);

        Operator operatorUpdate = new Operator(
                "???",
                "Kampfkollos",
                "Eric",
                "Cartman",
                "the.coon@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/"
                                + expectedOperator.operatorId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operatorUpdate)))
                .andExpect(status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + "/id/"
                        + expectedOperator.operatorId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.username").value("Kampfkollos"))
                .andExpect(jsonPath("$.firstName").value("Eric"))
                .andExpect(jsonPath("$.lastName").value("Cartman"))
                .andExpect(jsonPath("$.email").value("the.coon@southpark.com"))
                .andExpect(jsonPath("$.testBench.length()").value(0))
                .andExpect(jsonPath("$.role").value("OPERATOR"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "OPERATOR")
    void deleteOperator_whenLoggedIn_thenStatusNoContent() throws Exception {

        Operator operator = new Operator(
                "1",
                "Kampfkollos",
                "Eric",
                "Cartman",
                "eric.catrman@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated())
                .andReturn();

        Operator operatorToDelete = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Operator.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + operatorToDelete.operatorId()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteOperator_whenNotLoggedInAsOperator_thenStatusForbidden() throws Exception {

        Operator operator = new Operator(
                "1",
                "Kampfkollos",
                "Eric",
                "Cartman",
                "eric.catrman@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isCreated())
                .andReturn();

        Operator operatorToDelete = objectMapper
                .readValue(response
                        .getResponse()
                        .getContentAsString(), Operator.class);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + operatorToDelete.operatorId()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DirtiesContext
    void deleteOperator_whenNotLoggedIn_thenStatusUnauthorized() throws Exception {

        Operator operator = new Operator(
                "1",
                "Kampfkollos",
                "Eric",
                "Cartman",
                "eric.catrman@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        operatorRepository.save(operator);

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/"
                        + operator.operatorId()))
                .andExpect(status().isUnauthorized());
    }
}
