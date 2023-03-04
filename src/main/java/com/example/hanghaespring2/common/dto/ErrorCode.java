package com.example.hanghaespring2.common.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {



    //sdfdsfdsf
    NOT_NULL(400,"필수값이 누락되었습니다"),
    PATTERN(400, "잘못된 양식의 값이 있습니다."),
    TOKEN_AUTH_ERROR(400, "토큰 인증 오류"),
    TOKEN_EXPIRE_ERROR(400, "토큰 만료"),
    TOKEN_EMPTY_ERROR(400, "토큰이 없습니다."),
    AUTHORISE_ERROR(403, "인증 오류");

    private final Integer code;

    private final String description;

    ErrorCode(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
