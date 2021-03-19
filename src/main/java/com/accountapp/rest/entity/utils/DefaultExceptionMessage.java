package com.accountapp.rest.entity.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DefaultExceptionMessage {
    private String message;
}
