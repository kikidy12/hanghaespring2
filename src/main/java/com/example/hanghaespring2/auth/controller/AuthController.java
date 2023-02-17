package com.example.hanghaespring2.auth.controller;


import com.example.hanghaespring2.auth.dto.AuthDto;
import com.example.hanghaespring2.auth.service.AuthService;
import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.user.dto.UserDto;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Tag(name = "Account")
@RequestMapping("/api/")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @SecurityRequirements()
    public ResponseDto<UserDto.UserRes> addUser(@RequestBody @Valid AuthDto.UserAdd dto) {
        return ResponseDto.of(HttpStatus.OK, "회원가입 성공",this.authService.addUser(dto));
    }

    @PostMapping("/login")
    @SecurityRequirements()
    public ResponseDto<UserDto.UserRes> login(@RequestBody @Valid AuthDto.Login dto, HttpServletResponse response) {
        return ResponseDto.of(HttpStatus.OK, "로그인 성공",this.authService.login(dto, response));
    }
}
