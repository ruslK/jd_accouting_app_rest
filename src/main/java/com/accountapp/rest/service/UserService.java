package com.accountapp.rest.service;

import com.accountapp.rest.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User findByUserName(String userName) throws Exception;

    User createNewUser(User user) throws Exception;
}
