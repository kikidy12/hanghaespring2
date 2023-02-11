package com.example.hanghaespring2.account.controller;


import com.example.hanghaespring2.account.dto.AccountDto;
import com.example.hanghaespring2.account.service.AccountService;
import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.user.dto.UserDto;
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
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseDto<UserDto.UserRes> addUser(@RequestBody @Valid AccountDto.UserAdd dto) {
        return ResponseDto.of(HttpStatus.OK, "회원가입 성공",this.accountService.addUser(dto));
    }

    @PostMapping("/login")
    public ResponseDto<UserDto.UserRes> login(@RequestBody @Valid AccountDto.Login dto, HttpServletResponse response) {
        return ResponseDto.of(HttpStatus.OK, "로그인 성공",this.accountService.login(dto, response));
    }
}
