package com.example.hanghaespring2.post.repository;

import com.example.hanghaespring2.common.entity.Post;
import com.example.hanghaespring2.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
//    Optional<Post> findByIdAndUser_id(Long id, Long userId);

    Optional<Post> findByIdAndUser(Long id, User user);

}
