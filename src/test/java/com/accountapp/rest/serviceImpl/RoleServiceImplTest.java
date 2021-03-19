package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.Utils.TestUtils;
import com.accountapp.rest.entity.Role;
import com.accountapp.rest.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    RoleServiceImpl roleServices;

    @Test
    void getRolesForRootTest() {
        TestUtils.onSetUpAuthentication("Root");
        List<Role> roles = new ArrayList<>();
        lenient().when(roleRepository.getRolesForRoot()).thenReturn(roles);
        List<Role> foundRoles = roleServices.getRoles();
        verify(roleRepository, times(1)).getRolesForRoot();
        verify(roleRepository, never()).getRoles(Mockito.anyString());
        verify(roleRepository, only()).getRolesForRoot();
        verify(roleRepository, atMost(1)).getRolesForRoot();
        assertNotNull(foundRoles);
    }

    @Test
    void getRolesForAdminTest() {
        TestUtils.onSetUpAuthentication("Admin");
        List<Role> roles = new ArrayList<>();
        lenient().when(roleRepository.getRoles(Mockito.anyString())).thenReturn(roles);
        List<Role> foundRoles = roleServices.getRoles();
        verify(roleRepository, times(1)).getRoles("Admin");
        verify(roleRepository, never()).getRolesForRoot();
        verify(roleRepository, only()).getRoles("Admin");
        verify(roleRepository, atMost(1)).getRoles("Admin");
        assertNotNull(foundRoles);
    }
}