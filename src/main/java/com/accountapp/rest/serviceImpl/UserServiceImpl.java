package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.exception.ApplicationException;
import com.accountapp.rest.repository.UserRepository;
import com.accountapp.rest.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /**
     * Delete User base on Role,
     *      if ROOT - can delete only Admins, can't delete other users as Managers or Employees
     *      if Admin - can delete only users from same Company, can't delete Roots and other Admins,
     *          only can delete Managers or Employees
     * @param id - ID of account which need to delete
     * @return User object which was deleted
     * @throws ApplicationException - exception if User by ID not found
     */
    @Override
    public User deleteUser(Long id) throws ApplicationException {
        List<User> allUsers = this.getAllUsers().stream()
                .filter(user -> user.getId().equals(id)).collect(Collectors.toList());

        if (allUsers.isEmpty()) throw new ApplicationException("User ID " + id + " not found for your role");

        allUsers.get(0).setEnabled(false);
        allUsers.get(0).setUsername(allUsers.get(0).getUsername() + "_" + new Date().getTime());

        return userRepository.save(allUsers.get(0));
    }
}
