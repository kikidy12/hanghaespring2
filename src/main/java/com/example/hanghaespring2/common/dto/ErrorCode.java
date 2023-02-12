package com.example.hanghaespring2.common.dto;

import lombok.Getter;

public enum ErrorCode {

    NOT_NULL(400,"필수값이 누락되었습니다"),
    PATTERN(400, "잘못된 양식의 값이 있습니다.");

    @Getter
    private Integer code;

    @Getter
    private String description;

    ErrorCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
