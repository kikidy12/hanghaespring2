package com.example.hanghaespring2.post.service;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.hanghaespring2.post.repository.PostRepository;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public void addPost(PostDto.PostAdd dto) {

        User user = userRepository.findById(dto.getUserId()).orElseThrow(NullPointerException::new);
        Post post = Post.builder().dto(dto).user(user).build();

        this.postRepository.save(post);
    }
}
