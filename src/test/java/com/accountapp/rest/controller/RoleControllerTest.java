package com.accountapp.rest.controller;

import com.accountapp.rest.entity.Role;
import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.accountapp.rest.serviceImpl.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleControllerTest {

    @Mock
    private RoleServiceImpl roleService;

    @InjectMocks
    private RoleController roleController;

    @Test
    void getAllRolesForRoot() {
        List<Role> role = new ArrayList<>();
        role.add(Role.builder().name("Root").build());
        role.add(Role.builder().name("Admin").build());
        lenient().when(roleService.getRoles()).thenReturn(role);
        verify(roleService, atMost(1)).getRoles();
        ResponseEntity<ResponseWrapper> response = roleController.getAllRolesForRoot();
        List<Role> expectedRoles = (List<Role>) response.getBody().getData();
        assertNull(response.getBody().getCode());
        assertTrue(response.getBody().isSuccess());
        assertNull(response.getBody().getMessage());
        assertNotNull(response.getBody().getData());
        assertEquals(expectedRoles, role);
    }
}