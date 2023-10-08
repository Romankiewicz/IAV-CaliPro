package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.repository.MetrologistRepository;
import de.iav.backend.repository.TestBenchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestBenchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestBenchRepository testBenchRepository;

    @Autowired
    private MetrologistRepository metrologistRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String BASE_URL = "/api/testbenches";


    @Test
    @WithMockUser
    void getAllTestBenches_whenListIsEmpty_thenReturnEmptyList() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(content().json("[]"));
    }

    @Test
    void getTestBenchById() {
    }

    @Test
    void addTestBench() {
    }

    @Test
    void addMetrologyToTestBench() {
    }

    @Test
    void removeMetrologyFromTestBench() {
    }

    @Test
    void addTestBenchOperatorToTestBench() {
    }

    @Test
    void removeTestBechOperatorFromTestBench() {
    }
}
