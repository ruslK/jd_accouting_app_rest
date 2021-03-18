package com.accountapp.rest.service;

import com.accountapp.rest.entity.Role;
import com.accountapp.rest.entity.User;

import java.util.List;

public interface RoleServices {

    List<Role> getRolesBaseAuthentication (User user);
}
