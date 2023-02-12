package com.example.hanghaespring2.reply.controller;

import com.example.hanghaespring2.common.dto.ResponseDto;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.reply.service.ReplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Reply")
@RestController
@RequestMapping("/api/reply")
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;

    @PostMapping("/")
    public ResponseDto<ReplyDto.ReplyRes> addReply(@RequestBody ReplyDto.ReplyAdd dto) {
        return ResponseDto.of(HttpStatus.OK, "댓글 등록 성공", replyService.addReply(dto));
    }

    @PutMapping("/{id}")
    public ResponseDto<ReplyDto.ReplyRes> updateReply(@PathVariable Long id, @RequestBody ReplyDto.ReplyUpdate dto) {

        return ResponseDto.of(HttpStatus.OK, "댓글 수정 성공", replyService.updateReply(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseDto deleteReply(@PathVariable Long id) {
        replyService.deleteReply(id);
        return ResponseDto.of(HttpStatus.OK, "댓글 삭제 성공");
    }

}
