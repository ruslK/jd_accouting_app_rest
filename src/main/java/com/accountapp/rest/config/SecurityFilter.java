package com.accountapp.rest.config;

import com.accountapp.rest.entity.User;
import com.accountapp.rest.service.SecurityService;
import lombok.SneakyThrows;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTConfig jwtConfig;
    private final SecurityService securityService;

    public SecurityFilter(JWTConfig jwtConfig, SecurityService securityService) {
        this.jwtConfig = jwtConfig;
        this.securityService = securityService;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        String token = null;
        String username = null;

        if (authorizationHeader != null) {
            token = authorizationHeader.replace("Bearer", "");
            username = jwtConfig.extractUsername(token);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = securityService.loadUserByUsername(username);
            if (jwtConfig.validateToken(token, userDetails) && checkIfUserIsValid(username)) {
                UsernamePasswordAuthenticationToken currentUser =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                currentUser
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(currentUser);
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean checkIfUserIsValid(String username) throws Exception {
        User currentUser = securityService.loadUser(username);
        return currentUser != null && currentUser.getEnabled();
    }
}
