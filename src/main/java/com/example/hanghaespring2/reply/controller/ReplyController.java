package com.example.hanghaespring2.reply.controller;

import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.common.entity.UserRoleEnum;
import com.example.hanghaespring2.common.security.CustomUserDetail;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Reply")
@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/")
    public ResponseDto<ReplyDto.ReplyRes> addReply(
            @RequestBody ReplyDto.ReplyAdd dto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        return ResponseDto.of(HttpStatus.OK, "댓글 등록 성공", replyService.addReply(dto, userDetail.getUser()));
    }

    @PutMapping("/{id}")
    public ResponseDto<ReplyDto.ReplyRes> updateReply(
            @PathVariable Long id,
            @RequestBody ReplyDto.ReplyUpdate dto,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
    ) {

        return ResponseDto.of(HttpStatus.OK, "댓글 수정 성공", replyService.updateReply(id, dto, userDetail.getUser()));
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteReply(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        replyService.deleteReply(id, userDetail.getUser());
        return ResponseDto.of(HttpStatus.OK, "댓글 삭제 성공");
    }

    @PostMapping("/{id}/like")
    public ResponseDto updateReplyLike(
            @PathVariable Long id,
            @Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail
    ) {
        replyService.updateReplyLike(id, userDetail.getUser());
        return ResponseDto.of(HttpStatus.OK, "좋아요 업데이트 성공");
    }


}
