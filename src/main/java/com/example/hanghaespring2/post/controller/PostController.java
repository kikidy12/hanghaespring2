package com.example.hanghaespring2.post.controller;

import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import com.example.hanghaespring2.common.security.CustomUserDetail;
import com.example.hanghaespring2.post.dto.PostDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.example.hanghaespring2.post.service.PostService;

import java.util.List;

@Tag(name = "Post")
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @Secured(UserRoleEnum.Authority.USER)
    @PostMapping("/")
    public ResponseDto<PostDto.PostResNoReply> addPost(
            @RequestBody PostDto.PostAdd dto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        return ResponseDto.of(HttpStatus.OK, "게시글 등록 성공", postService.addPost(dto, userDetails.getUser()));
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
    public ResponseDto<PostDto.PostResNoReply> updatePost(
            @PathVariable Long id,
            @RequestBody PostDto.PostUpdate dto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        return ResponseDto.of(HttpStatus.OK, "게시글 수정 성공", postService.updatePost(id, dto, userDetails.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseDto deletePost(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetails
    ) {
        this.postService.deletePost(id, userDetails.getUser());
        return ResponseDto.of(HttpStatus.OK, "게시글 삭제 성공");
    }

    @PostMapping("/{id}/like")
    public ResponseDto updateReplyLike(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        postService.updateReplyLike(id, userDetail.getUser());
        return ResponseDto.of(HttpStatus.OK, "좋아요 업데이트 성공");
    }
}
