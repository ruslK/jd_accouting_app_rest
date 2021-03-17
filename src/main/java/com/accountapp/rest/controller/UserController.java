package com.accountapp.rest.controller;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.config.JWTConfig;
import com.accountapp.rest.entity.utils.AuthenticationRequest;
import com.accountapp.rest.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTConfig jwtConfig;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/v1/user")
    public List<User> getListOfUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/token")
    public String generateToken(@RequestBody AuthenticationRequest body) throws Exception {
        System.out.println(body);
        String password = body.getPassword();
        String username = body.getUsername();
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(authenticationToken);

        User foundUser = userService.findByUserName(username);
        if (!foundUser.getEnabled()) {
            throw new Exception("Account is not VERIFIED, Please Verify your account!");
//            throw new TicketingProjectException("Account is not VERIFIED, Please Verify your account!");
        }

        String jwtUtil = this.jwtConfig.generateToken(foundUser);
        return jwtUtil;
    }
}
