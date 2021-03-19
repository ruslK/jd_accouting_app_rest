package com.accountapp.rest.Utils;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {

    public static void onSetUpAuthentication(String role) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        authorities.add(grantedAuthority);
        UserDetails userDetails = new org.springframework.security.core
                .userdetails.User(
                "username",
                "password",
                authorities);
        UsernamePasswordAuthenticationToken currentUser =
                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(currentUser);
    }
}
