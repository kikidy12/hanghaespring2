package com.example.hanghaespring2.user.controller;


import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.user.dto.UserDto;
import com.example.hanghaespring2.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "User")
@RequestMapping("/api/user")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;
}
