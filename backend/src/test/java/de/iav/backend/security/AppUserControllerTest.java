package de.iav.backend.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AppUserRepository appUserRepository;

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
    void registerNewMetrologist_() {
    }

    @Test
    void registerNewOperator() {
    }

    @Test
    void logout() {
    }
}