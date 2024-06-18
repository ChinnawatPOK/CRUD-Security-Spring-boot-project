package com.crud.security.service;


import com.crud.security.config.JwtService;
import com.crud.security.model.auth.AuthenticationResponse;
import com.crud.security.model.auth.RegisterRequest;
import com.crud.security.model.auth.AuthenticationRequest;
import com.crud.security.model.auth.User;
import com.crud.security.model.auth.RegisterResponse;
import com.crud.security.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private UserRepository repository;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private  AuthenticationManager authenticationManager;

    @Test
    public void testRegisterWhenGenerateTokenSuccessThenReturnAccessToken() {
        RegisterRequest request = RegisterRequest
                .builder()
                .firstname("Chinnawat")
                .lastname("Test")
                .email("Chinnawat@gmail.com")
                .password("12345")
                .build();
        when(passwordEncoder.encode(any())).thenReturn("xxx");
        when(repository.save(any())).thenReturn(new User());
        RegisterResponse actualResponse = authenticationService.register(request);
        assertThat(actualResponse.getStatus(), equalTo("register success"));
    }

    @Test
    public void testAuthenticateWhenSendCorrectEmailANdPasswordThenReturnValidToken() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("Chinnawat@gmail.com")
                .password("12345")
                .build();
        User user = User.builder()
                .id(1)
                .email("Chinnawat@gmail.com")
                .password("xxx")
                .build();
        when(authenticationManager.authenticate(any())).thenReturn(null);
        when(repository.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        AuthenticationResponse actualResponse = authenticationService.authenticate(request);
        verify(repository, times(1)).findByEmail(argumentCaptor.capture());
        assertThat(actualResponse.getAccessToken(), equalTo("jwtToken"));
        assertThat(argumentCaptor.getValue(), equalTo("Chinnawat@gmail.com"));
    }
}
