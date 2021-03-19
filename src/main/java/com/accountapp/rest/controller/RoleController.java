package com.accountapp.rest.controller;

import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.accountapp.rest.service.RoleServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/role")
@Tag(name = "Role", description = "Controller for Role operation")
public class RoleController {

    private final RoleServices roleServices;

    public RoleController(RoleServices roleServices) {
        this.roleServices = roleServices;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('Root','Admin')")
    @Operation(summary = "Get all Roles", description = "Base on requester Role")
    public ResponseEntity<ResponseWrapper> getAllRolesForRoot() {
        return ResponseEntity.ok(
                new ResponseWrapper(roleServices.getRoles()));
    }
}
