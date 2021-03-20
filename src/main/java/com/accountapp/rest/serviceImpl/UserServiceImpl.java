package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.repository.UserRepository;
import com.accountapp.rest.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> getAllUsers() throws ApplicationException {
        Object role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toArray()[0];
        if (role.toString().equals("Root")) {
            return userRepository.getUsersManagedByRoot();
        } else if (role.toString().equals("Admin")) {
            return userRepository.getUsersManagedByAdmin(
                    Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName()));
        } else {
            throw new ApplicationException("Role - " + role.toString() + " - does not have access to get List of Users");
        }
    }

    @Override
    public User findByUserName(String userName) throws Exception {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new Exception("UserName: " + userName + " not found"));
    }

    @Override
    public User createNewUser(User user) throws Exception {
        Optional<User> foundUser = userRepository.findByUsername(user.getUsername());
        if (!foundUser.isEmpty()) {
            throw new Exception("User with username: " + user.getUsername() + " is already exist");
        }
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}
