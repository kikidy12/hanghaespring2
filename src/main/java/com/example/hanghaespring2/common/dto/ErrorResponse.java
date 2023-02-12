package com.example.hanghaespring2.common.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorResponse {

    private Integer code;

    private String description;

    private String detail;

    public ErrorResponse(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public ErrorResponse(Integer code, String description, String detail) {
        this.code = code;
        this.description = description;
        this.detail = detail;
    }
}