package com.accountapp.rest.service;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.exception.ApplicationException;

import java.util.List;

public interface UserService {
    List<User> getAllUsers() throws ApplicationException;

    User findByUserName(String userName) throws Exception;

    User createNewUser(User user) throws Exception;

    User deleteUser(Long id) throws ApplicationException;
}
