package de.iav.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.model.TestBench;
import de.iav.backend.repository.MetrologistRepository;
import de.iav.backend.repository.TestBenchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @WithMockUser
    @DirtiesContext
    void getAllTestBenches_whenTestBenchExists_thenReturnTestBenchList() throws Exception {

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDate.of(2022,2,20),
                LocalDate.of(2022,2,20)
        );

        testBenchRepository.save(testBench);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].benchId").value("1"))
                .andExpect(jsonPath("$[0].name").value("Pruefstand_1"))
                .andExpect(jsonPath("$[0].metrology.length()").value(0))
                .andExpect(jsonPath("$[0].operator.length()").value(0))
                .andExpect(jsonPath("$[0].maintenance").value("2022-02-20"))
                .andExpect(jsonPath("$[0].calibration").value("2022-02-20"));
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
