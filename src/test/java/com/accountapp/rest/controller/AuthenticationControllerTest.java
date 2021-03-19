package com.accountapp.rest.controller;

import com.accountapp.rest.Utils.TestUtils;
import com.accountapp.rest.config.JWTConfig;
import com.accountapp.rest.entity.User;
import com.accountapp.rest.entity.utils.AuthenticationRequest;
import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JWTConfig jwtConfig;
    @Mock
    private UserService userService;

    @InjectMocks
    AuthenticationController authenticationController;


    private AuthenticationRequest authenticationRequest;
    private UsernamePasswordAuthenticationToken authenticationToken;
    private User mockUser;

    @BeforeEach
    public void setUP() throws Exception {
        authenticationRequest =
                new AuthenticationRequest("username", "password");
        authenticationToken =
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword());
        mockUser = TestUtils.setUpUser();
        lenient().when(authenticationManager.authenticate(authenticationToken)).thenReturn(authenticationToken);
        lenient().when(userService.findByUserName(authenticationRequest.getUsername())).thenReturn(mockUser);
        lenient().when(jwtConfig.generateToken(mockUser)).thenReturn("TestToken");
    }

    @Test
    void generateTokenTest() throws Exception {
        ResponseEntity<ResponseWrapper> token = authenticationController.generateToken(authenticationRequest);
        assertEquals(token.getBody().getMessage(), "TestToken");
    }

    @Test
    void generateTokenExceptionTest() throws Exception {
        mockUser.setEnabled(false);
        lenient().when(userService.findByUserName(authenticationRequest.getUsername()))
                .thenThrow(new RuntimeException("Account is not verified"));
        lenient().when(jwtConfig.generateToken(mockUser)).thenReturn("TestToken");
        Throwable exception = assertThrows(RuntimeException.class,
                () -> authenticationController.generateToken(authenticationRequest));
        assertEquals(exception.getMessage(), "Account is not verified");
    }

    @Test
    void generateTokenInternalExceptionTest() throws Exception {
        mockUser.setEnabled(false);
        lenient().when(userService.findByUserName(authenticationRequest.getUsername()))
                .thenReturn(mockUser);
        Throwable exception = assertThrows(ApplicationException.class,
                () -> authenticationController.generateToken(authenticationRequest));
        assertEquals(exception.getMessage(), "Account is not VERIFIED, Please Verify your account!");
    }
}