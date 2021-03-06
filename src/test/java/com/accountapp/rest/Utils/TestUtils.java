package com.accountapp.rest.Utils;

import com.accountapp.rest.entity.Role;
import com.accountapp.rest.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static void onSetUpAuthentication(String role) {
        UserDetails userDetails = setUpUserDetails(role);
        UsernamePasswordAuthenticationToken currentUser =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(currentUser);
    }

    public static UserDetails setUpUserDetails(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        authorities.add(grantedAuthority);
        UserDetails userDetails = new org.springframework.security.core
                .userdetails.User(
                "username",
                "password",
                authorities);
        return userDetails;
    }

    public static User setUpUser() {
        User user = new User();
        Role role = new Role();
        role.setName("Root");
        user.setRole(role);
        user.setId(14234234L);
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        return user;
    }
}
