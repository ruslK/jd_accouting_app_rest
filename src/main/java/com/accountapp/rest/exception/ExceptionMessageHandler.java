package com.accountapp.rest.exception;

import com.accountapp.rest.entity.utils.DefaultExceptionMessage;
import com.accountapp.rest.entity.utils.ResponseWrapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Method;
import java.util.Optional;

@RestControllerAdvice
public class ExceptionMessageHandler {

    @ExceptionHandler({Exception.class, RuntimeException.class, Throwable.class, JwtException.class,
            BadCredentialsException.class, ExpiredJwtException.class})
    public ResponseEntity<ResponseWrapper> genericException(Throwable e, HandlerMethod handlerMethod) {
        Optional<DefaultExceptionMessage> defaultMessage = getMessageFromAnnotation(handlerMethod.getMethod());
        if (defaultMessage.isPresent() && !ObjectUtils.isEmpty(defaultMessage.get().getMessage())) {
            ResponseWrapper response = ResponseWrapper
                    .builder()
                    .success(false)
                    .message(defaultMessage.get().getMessage())
                    .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(ResponseWrapper.builder().success(false)
                .message(e.getMessage())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Optional<DefaultExceptionMessage> getMessageFromAnnotation(Method method) {
        com.accountapp.rest.annotation.DefaultExceptionMessage defaultExceptionMessage =
                method.getAnnotation(com.accountapp.rest.annotation.DefaultExceptionMessage.class);
        if (defaultExceptionMessage != null) {
            DefaultExceptionMessage defaultExceptionMessageDto = DefaultExceptionMessage
                    .builder()
                    .message(defaultExceptionMessage.defaultMessage())
                    .build();
            return Optional.of(defaultExceptionMessageDto);
        }
        return Optional.empty();
    }
}
