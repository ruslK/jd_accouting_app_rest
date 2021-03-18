package com.accountapp.rest.controller;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@PreAuthorize("hasAnyAuthority('Root','Admin')")
@Tag(name = "User", description = "Controller for User operation")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @Operation(summary = "Get All Users", description = "Base on requester Role")
    public List<User> getListOfUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Base on requester Role")
    public User createNewUser(@RequestBody User user) throws Exception {
        return userService.createNewUser(user);
    }


}
