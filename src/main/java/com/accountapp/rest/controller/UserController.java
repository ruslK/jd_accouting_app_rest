package com.accountapp.rest.controller;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public List<User> getListOfUsers() throws ApplicationException {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Get User By ID")
    public User getUserById(@PathVariable Long id) throws Exception {
        return userService.getUserBaseOnRoleById(id);
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Base on requester Role")
    public User createNewUser(@Valid @RequestBody User user) throws Exception {
        return userService.createNewUser(user);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete User", description = "Delete User by ID")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable Long id) throws ApplicationException {
        userService.deleteUser(id);
        return ResponseEntity.ok(new ResponseWrapper("Account ID " + id + " - DELETED"));
    }

    @PutMapping
    @Operation(summary = "Update User", description = "Update User by ID")
    public ResponseEntity<ResponseWrapper> updateUser(@Valid @RequestBody User user) throws Exception {
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(new ResponseWrapper("Account ID " + user.getId() + " - Updated",
                updatedUser));
    }
}
