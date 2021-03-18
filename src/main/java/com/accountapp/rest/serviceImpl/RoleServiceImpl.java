package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.entity.Role;
import com.accountapp.rest.repository.RoleRepository;
import com.accountapp.rest.service.RoleServices;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleServices {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        Object role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        if (role.equals("Root")) {
            return roleRepository.getRolesForRoot();
        } else {
            return roleRepository.getRoles("Admin");
        }
    }
}