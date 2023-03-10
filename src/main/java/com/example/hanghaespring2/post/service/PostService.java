package com.example.hanghaespring2.post.service;

import com.example.hanghaespring2.common.entity.*;
import com.example.hanghaespring2.common.util.CustomClientException;
import com.example.hanghaespring2.post.dto.OrderDto;
import com.example.hanghaespring2.post.dto.PostDto;
import com.example.hanghaespring2.post.dto.TestDto;
import com.example.hanghaespring2.post.repository.PostRepositoryImpl;
import com.example.hanghaespring2.postCategory.repository.PostCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.example.hanghaespring2.post.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final PostCategoryRepository postCategoryRepository;

    private final PostRepositoryImpl postRepositoryImpl;

    @Transactional
    public PostDto.PostResNoReply addPost(PostDto.PostAdd dto, User user) {
        PostCategory category = this.postCategoryRepository.findById(dto.getCategoryId()).orElseThrow(() ->
                new CustomClientException("카테고리가 존재하지 않습니다.")
        );
        Post post = Post.builder().dto(dto).user(user).category(category).build();

        return this.postRepository.save(post).resNoReply();
    }

    @Transactional
    public PostDto.PostResNoReply updatePost(Long id, PostDto.PostUpdate dto, User user) {
        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new CustomClientException("게시글이 존재하지 않습니다.")
        );

        if (user.getRole() != UserRoleEnum.ADMIN) {
            if (post.getUser().getId().equals(user.getId())) {
                throw new CustomClientException("작성자만 삭제/수정할 수 있습니다.");
            }
        }

        post.update(dto);

        return post.resNoReply();
    }

    @Transactional
    public void deletePost(Long id, User user) {

        Post post = this.postRepository.findById(id).orElseThrow(() ->
                new CustomClientException("게시글이 존재하지 않습니다."));

        if (user.getRole() != UserRoleEnum.ADMIN) {
            if (!post.getUser().getId().equals(user.getId())) {
                throw new CustomClientException("작성자만 삭제/수정할 수 있습니다.");
            }
        }


        this.postRepository.deleteById(post.getId());
    }


    @Transactional(readOnly = true)
    public List<PostDto.PostRes> getPosts(int page, int size, String sortBy, boolean isAsc) {
        // 페이징 처리
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
//
//        return this.postRepository.findAll(pageable).map(Post::res).stream().toList();

        //jpql 사용
        List<Post> postSet = this.postRepository.selectAllJPQL(pageable);
//        return postSet.stream().map(Post::res).toList();
        return postRepositoryImpl.getPost3();
    }

    public PostDto.PostRes getPost(Long id) {

        List<TestDto> dtos = postRepositoryImpl.getUser2();

//        List<PostDto.PostRes> test = postRepositoryImpl.getPost3();
//
//        for (PostDto.PostRes postRes : test) {
//            System.out.println(postRes.getTitle());
//            System.out.println(postRes.getUsername());
//        }

        for (TestDto dto : dtos) {
            System.out.println(dto.getUserName());
            System.out.println(dto.getId());
            for (OrderDto orderDto : dto.getOrderDtoList()) {
                System.out.println(orderDto.getUserId());
            }
            System.out.println("=======================");
        }
        System.out.println("============start=============");
        Post post = this.postRepository.selectJPQL(id);
        System.out.println("============second=============");

//        Post post = this.postRepository.findById(id).orElseThrow(() ->
//                new CustomClientException("게시글이 존재하지 않습니다.")
//        );

        return post.res();
    }

    @Transactional
    public void updateReplyLike(Long id, User user) {

        Post post = postRepository.findByIdAndUser(id, user).orElseThrow(
                () -> new CustomClientException("해당하는 게시물이 없습니다.")
        );

        if(post.getLikeUsers().stream().anyMatch(v -> Objects.equals(v.getUser().getId(), user.getId()))) {
            post.removeLikeUser(user);
        }
        else {
            PostLikeUser likeUser = PostLikeUser.builder().user(user).post(post).build();
            post.addLikeUser(likeUser);
        }
    }
}
