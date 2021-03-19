package com.accountapp.rest.controller;

import com.accountapp.rest.config.JWTConfig;
import com.accountapp.rest.entity.User;
import com.accountapp.rest.entity.utils.AuthenticationRequest;
import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Authentication", description = "Controller for Authentication")
@RequestMapping("/authentication")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JWTConfig jwtConfig;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager,
                                    JWTConfig jwtConfig, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Get Token", description = "Generate token after authentication")
    public ResponseEntity<ResponseWrapper> generateToken(@RequestBody AuthenticationRequest body) throws Exception {
        String password = body.getPassword();
        String username = body.getUsername();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);

        User foundUser = userService.findByUserName(username);
        if (!foundUser.getEnabled()) {
            throw new ApplicationException("Account is not VERIFIED, Please Verify your account!");
        }

        String jwtUtil = this.jwtConfig.generateToken(foundUser);
        return ResponseEntity.ok(new ResponseWrapper(jwtUtil));
    }
}
