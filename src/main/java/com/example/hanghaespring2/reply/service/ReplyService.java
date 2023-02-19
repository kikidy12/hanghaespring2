package com.example.hanghaespring2.reply.service;

import com.example.hanghaespring2.common.entity.*;
import com.example.hanghaespring2.common.security.SecurityService;
import com.example.hanghaespring2.common.util.CustomClientException;
import com.example.hanghaespring2.post.repository.PostRepository;
import com.example.hanghaespring2.reply.dto.ReplyDto;
import com.example.hanghaespring2.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {

    private final SecurityService securityService;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public ReplyDto.ReplyRes addReply(ReplyDto.ReplyAdd dto, User user) {

        Post post = postRepository.findById(dto.getPostId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 게시글이 없습니다.")
        );

        Reply reply = Reply.builder().message(dto.getMessage()).user(user).post(post).build();

        return replyRepository.save(reply).res();
    }

    @Transactional
    public ReplyDto.ReplyRes addChildReply(ReplyDto.ChildReplyAdd dto, User user) {

        Reply parent = replyRepository.findById(dto.getParentId()).orElseThrow(
                () -> new IllegalArgumentException("해당하는 댓글이 없습니다.")
        );

        Reply child = Reply.builder().message(dto.getMessage()).user(user).parent(parent).build();

        return replyRepository.save(child).res();
    }

    @Transactional
    public ReplyDto.ReplyRes updateReply(Long id, ReplyDto.ReplyUpdate dto, User user) {
        Reply reply = replyRepository.findById(id).orElseThrow(
                () -> new CustomClientException("해당하는 댓글이 없습니다.")
        );

        if (user.getRole() != UserRoleEnum.ADMIN) {
            if (reply.getUser().getId().equals(user.getId())) {
                throw new CustomClientException("작성자만 삭제/수정할 수 있습니다.");
            }
        }

        reply.update(dto.getMessage());

        return reply.res();
    }

    @Transactional
    public void deleteReply(Long id, User user) {
        Reply reply = replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new CustomClientException("해당하는 댓글이 없습니다.")
        );

        if (user.getRole() != UserRoleEnum.ADMIN) {
            if (reply.getUser().getId().equals(user.getId())) {
                throw new CustomClientException("작성자만 삭제/수정할 수 있습니다.");
            }
        }

        replyRepository.delete(reply);
    }


    @Transactional
    public void updateReplyLike(Long id, User user) {

        Reply reply = replyRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new CustomClientException("해당하는 댓글이 없습니다.")
        );

        if(reply.getLikeUsers().stream().anyMatch(v -> Objects.equals(v.getUser().getId(), user.getId()))) {
            reply.removeLikeUser(user);
            replyRepository.save(reply);
        }
        else {
            ReplyLikeUser likeUser = ReplyLikeUser.builder().user(user).reply(reply).build();
            reply.addLikeUser(likeUser);
        }
    }
}
