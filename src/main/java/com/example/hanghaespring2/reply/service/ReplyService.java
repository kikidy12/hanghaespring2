package com.example.hanghaespring2.reply.service;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.Reply;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.util.SecurityService;
import com.example.hanghaespring2.post.repository.PostRepository;
import com.example.hanghaespring2.post.service.PostService;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final SecurityService securityService;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyDto.ReplyRes addReply(ReplyDto.ReplyAdd dto) {
        User user = securityService.getUser();

        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );

        Reply reply = Reply.builder().message(dto.getMessage()).user(user).post(post).build();

        return replyRepository.save(reply).res();
    }

    @Transactional
    public ReplyDto.ReplyRes updateReply(Long id, ReplyDto.ReplyUpdate dto) {
        User user = securityService.getUser();

        Reply reply = replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        reply.update(dto.getMessage());

        return reply.res();
    }

    @Transactional
    public void deleteReply(Long id) {
        User user = securityService.getUser();

        Reply reply = replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );
        replyRepository.delete(reply);
    }

}
