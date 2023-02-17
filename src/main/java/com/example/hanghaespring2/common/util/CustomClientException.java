package com.example.hanghaespring2.common.util;

import org.springframework.http.HttpStatus;

public class CustomClientException extends IllegalArgumentException {


    public CustomClientException(String s) {
        super(s);
    }
}
