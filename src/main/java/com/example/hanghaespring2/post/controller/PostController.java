package com.example.hanghaespring2.post.controller;

import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.post.dto.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.hanghaespring2.post.service.PostService;

import java.util.List;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/")
    public ResponseDto<PostDto.PostRes> addPost(@RequestBody PostDto.PostAdd dto) {
        return ResponseDto.of(HttpStatus.OK, "게시글 등록 성공", postService.addPost(dto));
    }

    @GetMapping("/")
    @SecurityRequirements()
    public ResponseDto<List<PostDto.PostRes>> getPosts(
            @Parameter(example = "1") int page,
            @Parameter(example = "10") int size,
            @Parameter(example = "title") String sortBy,
            @Parameter(example = "false") boolean isAsc
    ) {
        return ResponseDto.of(HttpStatus.OK, "게시글 조회 성공", postService.getPosts(page-1, size, sortBy, isAsc));
    }

    @GetMapping("/{id}")
    @SecurityRequirements()
    public ResponseDto<PostDto.PostRes> getPosts(@PathVariable Long id) {
        return ResponseDto.of(HttpStatus.OK, "게시글 조회 성공", postService.getPost(id));
    }


    @PutMapping("/{id}")
    public ResponseDto<PostDto.PostRes> updatePost(@PathVariable Long id, @RequestBody PostDto.PostUpdate dto) {
        return ResponseDto.of(HttpStatus.OK, "게시글 수정 성공", postService.updatePost(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto deletePost(@PathVariable Long id) {
        this.postService.deletePost(id);
        return ResponseDto.of(HttpStatus.OK, "게시글 삭제 성공");
    }
}
