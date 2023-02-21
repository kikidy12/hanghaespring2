package com.example.hanghaespring2.post.repository;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import com.example.hanghaespring2.post.dto.PostDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

//    Optional<Post> findByIdAndUser_id(Long id, Long userId);

    Optional<Post> findByIdAndUser(Long id, User user);

    @Query(value = "select p from Post p where p.id = :id")
    @EntityGraph(attributePaths = {
            "category", "user",
            "likeUsers", "replies.likeUsers",
            "replies.children", "replies.children.likeUsers"
    })
    Post selectJPQL(Long id);

    @Query(value = "select p from Post p")
    @EntityGraph(attributePaths = {
            "category", "user",
            "likeUsers", "replies.likeUsers",
            "replies.children", "replies.children.likeUsers"
    })
    Set<Post> selectAllJPQL();
}
