package de.iav.backend.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.iav.backend.exceptions.MetrologistAlreadyExistException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

    private final ObjectMapper objectMapper =new ObjectMapper();

    private final String BASE_URL = "/api/users";

    @Test
    @DirtiesContext
    @WithMockUser("user")
    void getMe_whenLoggedIn_thenReturnUser() throws Exception {

       mockMvc.perform(get(BASE_URL + "/me"))
               .andExpect(status().isOk())
               .andExpect(content().string("user"));
    }

    @Test
    @DirtiesContext
    void getMe_whenLoggedIn_thenReturnAnonymousUser() throws Exception {

        mockMvc.perform(get(BASE_URL + "/me"))
                .andExpect(status().isOk())
                .andExpect(content().string("anonymousUser"));
    }

    @Test
    @DirtiesContext
    @WithMockUser("user")
    void login_whenLogin_thenReturnUsername() throws Exception {

        mockMvc.perform(post(BASE_URL + "/login"))
                .andExpect(status().isOk())
                .andExpect(content().string("user"));
    }

    @Test
    @DirtiesContext
    void registerNewMerologist_whenMetrologistDontExist_thenExpectStatusIsOk() throws Exception {

        AppUser metrologist = new AppUser(
                "1",
                "SolidSnake",
                "1234",
                "snakeeater@foxhound.tz",
                UserRole.METROLOGIST);


        mockMvc.perform(post(BASE_URL + "/register/metrologist")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(metrologist)))
                .andExpect(status().isOk());

    }

//    @Test
//    @DirtiesContext
//    void registerNewMerologist_whenMetrologistExist_thenExpectStatusBadRequest() throws Exception {
//
//        AppUser metrologist = new AppUser(
//                "1",
//                "SolidSnake",
//                "1234",
//                "snakeeater@foxhound.tz",
//                UserRole.METROLOGIST);
//
//        appUserRepository.save(metrologist);
//
//        mockMvc.perform(post(BASE_URL + "/register/metrologist")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(metrologist)))
//                .andExpect(result -> {
//                    // Überprüfen Sie, ob die Exception MetrologistAlreadyExists ist
//                    assertThat(result.getResolvedException()).isInstanceOf(MetrologistAlreadyExistException.class);
//                });
//
//    }
    @Test
    @DirtiesContext
    void registerNewMerologist_whenMetrologistExist_thenExpectMetrologistAlreadyExistException() throws Exception {
        AppUser metrologist = new AppUser(
                "1",
                "SolidSnake",
                "1234",
                "snakeeater@foxhound.tz",
                UserRole.METROLOGIST);

        appUserRepository.save(metrologist);

        // Verwenden Sie assertThrows, um die erwartete Ausnahme abzufangen
        ServletException exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(post(BASE_URL + "/register/metrologist")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(metrologist)))
                    .andExpect(status().isBadRequest());
        });

        // Überprüfen Sie, ob die Ausnahme den erwarteten Text oder weitere Details enthält
        assertThat(exception.getMessage()).isEqualTo("Request processing failed: de.iav.backend.exceptions.MetrologistAlreadyExistException: User already taken!");
    }

    @Test
    @DirtiesContext
    void registerNewOperator_whenOperatorDontExist_thenExpectStatusIsOk() throws Exception {

        AppUser operator = new AppUser(
                "1",
                "SolidSnake",
                "1234",
                "snakeeater@foxhound.tz",
                UserRole.METROLOGIST);


        mockMvc.perform(post(BASE_URL + "/register/operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(operator)))
                .andExpect(status().isOk());

    }

    @Test
    void logout() {
    }
}