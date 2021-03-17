package com.accountapp.rest.entity.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ResponseWrapper {

    private Integer code;
    private boolean success;
    private String message;
    private Object data;

    public ResponseWrapper(String message, Object data, Integer code) {
        this.message = message;
        this.data = data;
        this.code = code;
        this.success = true;
    }

    public ResponseWrapper(String message, Object data) {
        this.message = message;
        this.data = data;
        this.code = HttpStatus.OK.value();
        this.success = true;
    }

    public ResponseWrapper(String message) {
        this.message = message;
        this.code = HttpStatus.OK.value();
        this.success = true;
    }
}
