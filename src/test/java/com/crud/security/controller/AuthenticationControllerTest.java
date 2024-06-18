package com.crud.security.controller;

import com.crud.security.model.auth.AuthenticationRequest;
import com.crud.security.model.auth.AuthenticationResponse;
import com.crud.security.model.auth.RegisterResponse;
import com.crud.security.model.auth.User;
import com.crud.security.service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriComponentsBuilder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Test
    public void testRegisterThenSuccess() throws Exception {
        String url = UriComponentsBuilder.fromPath("/api/auth/register").build().toUriString();
        when(authenticationService.register(any())).thenReturn(
                RegisterResponse.builder().status("register success").build());
        User user = User.builder()
                .firstname("Chinnawat")
                .lastname("Kaewchim")
                .email("chinnawat@gmail.com")
                .password("12345")
                .build();

        String reqBody = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.status").value("register success"));
    }

    @Test
    public void testLoginThenSuccess() throws Exception {
        String url = UriComponentsBuilder.fromPath("/api/auth/authenticate").build().toUriString();
        AuthenticationResponse response = AuthenticationResponse.builder().accessToken("accessToken").build();
        when(authenticationService.authenticate(any())).thenReturn(response);
        AuthenticationRequest user = AuthenticationRequest.builder()
                .email("chinnawat@gmail.com")
                .password("12345")
                .build();

        String reqBody = new ObjectMapper().writeValueAsString(user);

        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .content(reqBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.access_token").value("accessToken"));
    }

}
