package com.springsport.core.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsport.core.security.JwtUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtUtil jwtUtil;

    @MockBean
    private AuthenticationManager authenticationManager;

    @Test
    @DisplayName("POST /login - should authenticate and return JWT token")
    void shouldReturnJwtOnSuccessfulLogin() throws Exception {
        String username = "testuser";
        String password = "testpass";

        Authentication auth = new UsernamePasswordAuthenticationToken(username, password);
        when(authenticationManager.authenticate(any(Authentication.class))).thenReturn(auth);

        when(jwtUtil.generateToken(username)).thenReturn("dummy-jwt-token");

        Map<String, String> loginRequest = Map.of("username", username, "password", password);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("dummy-jwt-token"));
    }

    @Test
    @DisplayName("POST /login - should return 401 on invalid credentials")
    void shouldReturn401OnInvalidLogin() throws Exception {
        // Mock authentication failure
        when(authenticationManager.authenticate(any(Authentication.class)))
                .thenThrow(new AuthenticationException("Bad credentials") {});

        Map<String, String> loginRequest = Map.of("username", "wrong", "password", "bad");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}
