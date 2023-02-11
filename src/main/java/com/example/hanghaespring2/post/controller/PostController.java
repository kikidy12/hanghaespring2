package com.example.hanghaespring2.post.controller;

import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.post.dto.PostDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.hanghaespring2.post.service.PostService;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public ResponseDto<?> addPost(@RequestBody PostDto.PostAdd dto) {
        postService.addPost(dto);
        return ResponseDto.builder().code(HttpStatus.OK.value()).message("등록 성공").data(null).build();
    }
}
