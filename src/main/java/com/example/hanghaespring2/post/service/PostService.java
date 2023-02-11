package com.example.hanghaespring2.post.service;

import com.example.hanghaespring2.auth.service.CustomUserDetailService;
import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.common.util.SecurityService;
import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.example.hanghaespring2.post.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final SecurityService securityService;

    @Transactional
    public PostDto.PostRes addPost(PostDto.PostAdd dto) {

        User user = securityService.getUser();

        Post post = Post.builder().dto(dto).build();

        post.setUser(user);

        return this.postRepository.save(post).res();
    }

    @Transactional
    public PostDto.PostRes updatePost(Long id, PostDto.PostUpdate dto) {

        User user = securityService.getUser();

        Post post = this.postRepository.findByIdAndUser_id(id, user.getId()).orElseThrow(() ->
            new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        post.update(dto);

        return post.res();
    }

    @Transactional
    public void deletePost(Long id) {

        User user = securityService.getUser();

        Post post = this.postRepository.findByIdAndUser_id(id, user.getId()).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
        );

        this.postRepository.deleteById(post.getId());
    }


    @Transactional(readOnly = true)
    public List<PostDto.PostRes> getPosts(int page, int size, String sortBy, boolean isAsc) {

        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return this.postRepository.findAll(pageable).map(Post::res).stream().toList();
    }

    public PostDto.PostRes getPost(Long id) {
        return this.postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("게시글이 존재하지 않습니다.")
        ).res();
    }
}
