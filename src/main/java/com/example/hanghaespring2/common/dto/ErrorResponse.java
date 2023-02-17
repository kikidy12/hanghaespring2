package com.example.hanghaespring2.common.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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


    public static void setErrorResponse (
            HttpServletResponse response,
            ErrorCode errorCode
    ){
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(errorCode.getCode());
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ErrorResponse errorResponse = new ErrorResponse(errorCode.getCode(), errorCode.getDescription());
        try{
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}