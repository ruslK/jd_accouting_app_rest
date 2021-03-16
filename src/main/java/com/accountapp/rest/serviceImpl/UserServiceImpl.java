package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.repository.UserRepository;
import com.accountapp.rest.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUsername(userName).get();
    }
}
