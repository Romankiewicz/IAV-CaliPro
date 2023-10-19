package de.iav.backend.controller;

import de.iav.backend.model.Metrology;
import de.iav.backend.model.Operator;
import de.iav.backend.model.TestBench;
import de.iav.backend.repository.MetrologyRepository;
import de.iav.backend.repository.OperatorRepository;
import de.iav.backend.repository.TestBenchRepository;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TestBenchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TestBenchRepository testBenchRepository;

    @Autowired
    private MetrologyRepository metrologyRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    private final String BASE_URL = "/api/testbenches";
    private final String OPERATOR_URL = "/api/operator";

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

        Date date = getTestDate();

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$[0].benchId").value("1"))
                .andExpect(jsonPath("$[0].name").value("Pruefstand_1"))
                .andExpect(jsonPath("$[0].metrology.length()").value(0))
                .andExpect(jsonPath("$[0].operator.length()").value(0))
                .andExpect(jsonPath("$[0].maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$[0].calibration").value("2022-02-20T00:00:00.000+00:00"));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getTestBenchById_whenTestBenchExists_thenReturnTestBench() throws Exception {


        Date date = getTestDate();
        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));

    }

    @Test
    @DirtiesContext
    void addTestBench_whenNotLoggedIn_thenGetStatusUnauthorized() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                            {
                                                "benchId": "1",
                                                "name": "Pruefstand_1",
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
    void addTestBench_whenLoggedIn_thenGetStatusIsCreated() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                            {
                                                "benchId": "1",
                                                "name": "Pruefstand_1",
                                                "maintenance": "2022-02-20",
                                                "calibration": "2022-02-20"
                                            }
                                        """
                        ))
                .andExpect(status()
                        .isCreated())
                .andReturn();
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void addMetrologyToTestBench_whenTestBenchAndMetrologyExistsAndLoggedIn_thenReturnTestBenchWithAssingnedMetrology() throws Exception {

        Date date = getTestDate();
        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date
        );

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + testBench.benchId()
                                + "/metrology/" + metrology.metrologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metrology.metrologyId()))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(1))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void removeMetrologyFromTestBench_whenLoggedIn_thenReturnStatusIsNoConttent() throws Exception {

        Date date = getTestDate();

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        Metrology metrology = new Metrology(
                "1",
                "1",
                "Horiba",
                "MEXA",
                date,
                date
        );

        metrologyRepository.save(metrology);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + testBench.benchId()
                                + "/metrology/" + metrology.metrologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metrology.metrologyId()))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(1))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));


        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + "/" + testBench.benchId()
                                + "/metrology/" + metrology.metrologyId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(metrology.metrologyId()))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));
    }

    @Test
    @DirtiesContext
    @WithMockUser("METROLOGIST")
    void addTestBenchOperatorToTestBench_whenTestBenchAndOperatorExistsAndLoggedIn_thenReturnTestBenchWithAssingnedOperator() throws Exception {

        Date date = getTestDate();

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        Operator operator = new Operator(
                "1",
                "StandYourGround",
                "Stan",
                "Marsh",
                "stan.marsh@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        operatorRepository.save(operator);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + testBench.benchId()
                                + "/operator/" + operator.username())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(operator.operatorId()))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(1))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));

        mockMvc.perform(MockMvcRequestBuilders.get(OPERATOR_URL + "/id/" + operator.operatorId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.operatorId").value(operator.operatorId()))
                .andExpect(jsonPath("$.username").value("StandYourGround"))
                .andExpect(jsonPath("$.firstName").value("Stan"))
                .andExpect(jsonPath("$.lastName").value("Marsh"))
                .andExpect(jsonPath("$.email").value("stan.marsh@southpark.com"))
                .andExpect(jsonPath("$.role").value("OPERATOR"));
    }

    @Test
    @DirtiesContext
    @WithMockUser(roles = "METROLOGIST")
    void removeTestBechOperatorFromTestBench_whenLoggedIn_thenExpectStatusIsNoContentAndReturnTestBench() throws Exception {

        Date date = getTestDate();

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        Operator operator = new Operator(
                "1",
                "StandYourGround",
                "Stan",
                "Marsh",
                "stan.marsh@southpark.com",
                new ArrayList<>(),
                UserRole.OPERATOR);

        operatorRepository.save(operator);

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + testBench.benchId()
                                + "/operator/" + operator.username())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(operator.operatorId()))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(1))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));

        mockMvc.perform(MockMvcRequestBuilders.get(OPERATOR_URL + "/id/" + operator.operatorId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.operatorId").value(operator.operatorId()))
                .andExpect(jsonPath("$.username").value("StandYourGround"))
                .andExpect(jsonPath("$.firstName").value("Stan"))
                .andExpect(jsonPath("$.lastName").value("Marsh"))
                .andExpect(jsonPath("$.email").value("stan.marsh@southpark.com"))
                .andExpect(jsonPath("$.role").value("OPERATOR"));

        mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL
                        + "/" + testBench.benchId()
                        + "/operator/" + operator.username()))
                .andExpect(status().isNoContent())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));
    }


    @Test
    @DirtiesContext
    @WithMockUser
    void updateTestBenchByBenchId_whenLoggedIn_thenReturnUpdatedTestBench() throws Exception {

        Date date = getTestDate();

        TestBench testBench = new TestBench(
                "1",
                "Pruefstand_1",
                new ArrayList<>(),
                new ArrayList<>(),
                date,
                date
        );

        testBenchRepository.save(testBench);

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2022-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2022-02-20T00:00:00.000+00:00"));

        mockMvc.perform(MockMvcRequestBuilders.put(BASE_URL + "/" + testBench.benchId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                            {
                                                "name": "Pruefstand_1",
                                                "maintenance": "2023-02-20",
                                                "calibration": "2023-02-20"
                                            }
                                        """

                        ))
                .andExpect(status().isCreated())
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL
                        + "/"
                        + testBench.benchId()))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.benchId").value(testBench.benchId()))
                .andExpect(jsonPath("$.name").value("Pruefstand_1"))
                .andExpect(jsonPath("$.metrology.length()").value(0))
                .andExpect(jsonPath("$.operator.length()").value(0))
                .andExpect(jsonPath("$.maintenance").value("2023-02-20T00:00:00.000+00:00"))
                .andExpect(jsonPath("$.calibration").value("2023-02-20T00:00:00.000+00:00"));
    }
}
