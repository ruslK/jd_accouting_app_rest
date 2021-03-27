package com.accountapp.rest.exception;

import com.accountapp.rest.entity.utils.ResponseWrapper;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MethodArgumentNotValidExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<Object> handleMethodArgumentException(MethodArgumentNotValidException ex) {

        Map<String, String> mapErrors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(x -> mapErrors.put(
                x.getField(),
                x.getDefaultMessage() + ": " + x.getRejectedValue()));

        ResponseWrapper exceptionResponse = new ResponseWrapper(
                HttpStatus.BAD_REQUEST.value(),
                false,
                "Post Input Validation Failed",
                mapErrors);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

}
