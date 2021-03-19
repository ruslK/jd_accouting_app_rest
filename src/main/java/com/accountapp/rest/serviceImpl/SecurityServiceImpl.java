package com.accountapp.rest.serviceImpl;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.service.SecurityService;
import com.accountapp.rest.service.UserService;
import lombok.SneakyThrows;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SecurityServiceImpl implements SecurityService {

    private final UserService userService;

    public SecurityServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userService.findByUserName(s);
        if (user == null) throw new UsernameNotFoundException("Account with ID: " + s + ", does not exists");
        return new org.springframework.security.core
                .userdetails.User(user.getId().toString(), user.getPassword(), this.listAuthorities(user));
    }

    private Collection<? extends GrantedAuthority> listAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority auth = new SimpleGrantedAuthority(user.getRole().getName());
        authorities.add(auth);
        return authorities;
    }

    @Override
    public User loadUser(String param) throws Exception {
        return userService.findByUserName(param);
    }
}
