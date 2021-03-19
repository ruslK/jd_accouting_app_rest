package com.accountapp.rest.exception;

import com.accountapp.rest.entity.utils.ResponseWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class JwtExceptionHandler extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException e) {
            ResponseWrapper response = ResponseWrapper
                    .builder()
                    .success(false)
                    .message(e.getMessage())
                    .code(HttpStatus.FORBIDDEN.value())
                    .build();
            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.setContentType("application/json");
            httpServletResponse.getWriter().write(convertObjectToJson(response));
        }
    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}